import os

def split_file(path_main, path_ext):
    with open(path_main, 'r') as f:
        main_content = f.read()
    
    with open(path_ext, 'r') as f:
        ext_content = f.read()
        
    # find the object declaration
    obj_idx = main_content.find("object DimenS")
    if obj_idx == -1:
        print(f"Object not found in {path_main}")
        return
        
    # The header and imports should stay in both (or just main).
    # actually, the top-level functions are between the imports and the object
    # let's find the last import
    import_lines = []
    other_lines = []
    
    # Simple split
    lines = main_content[:obj_idx].split('\n')
    header_end = 0
    for i, line in enumerate(lines):
        if line.startswith('import '):
            header_end = i
    
    header_and_imports = '\n'.join(lines[:header_end + 1])
    top_level_funcs = '\n'.join(lines[header_end + 1:]).strip()
    
    # In DimenExtensions, we should add the top_level_funcs 
    # But wait, DimenExtensions also needs imports!
    # Let's extract the imports from main_content and append to ext_content if not present
    
    ext_lines = ext_content.split('\n')
    ext_import_idx = 0
    for i, line in enumerate(ext_lines):
        if line.startswith('import '):
            ext_import_idx = i
            
    # Add new imports from main to ext
    ext_imports = [line for line in ext_lines if line.startswith('import ')]
    main_imports = [line for line in lines if line.startswith('import ')]
    
    for imp in main_imports:
        if imp not in ext_imports:
            ext_lines.insert(ext_import_idx + 1, imp)
            ext_imports.append(imp)
            ext_import_idx += 1
            
    new_ext_content = '\n'.join(ext_lines)
    # append top level funcs
    new_ext_content += '\n\n' + top_level_funcs + '\n'
    
    # write back ext
    with open(path_ext, 'w') as f:
        f.write(new_ext_content)
        
    # write back main
    new_main_content = header_and_imports + '\n\n' + main_content[obj_idx:]
    with open(path_main, 'w') as f:
        f.write(new_main_content)

split_file('library/src/main/java/com/appdimens/dynamic/code/scaled/DimenSdp.kt', 'library/src/main/java/com/appdimens/dynamic/code/scaled/DimenExtensions.kt')
split_file('library/src/main/java/com/appdimens/dynamic/code/scaled/DimenSsp.kt', 'library/src/main/java/com/appdimens/dynamic/code/scaled/DimenSspExtensions.kt')

