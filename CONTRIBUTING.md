# Contributing to AppDimens (DYNAMIC)

First off, thank you for considering contributing to AppDimens\! It’s contributions like yours that make the developer experience better for everyone handling Android's screen fragmentation.

## How Can I Contribute?

### 1\. Reporting Bugs 🐛

If you find a calculation error, a rendering glitch, or a crash:

  * Check the [Issues](https://www.google.com/search?q=https://github.com/bodenberg/appdimens-sdps/issues) page to see if it has already been reported.
  * If not, open a new issue. Please include:
      * The device model or screen density (DPI) where the issue occurred.
      * A brief code snippet or XML layout showing how the library was used.
      * The expected vs. actual result.

### 2\. Suggesting Enhancements 💡

Have an idea for a new scaling strategy or support for more screen buckets?

  * Open an issue with the tag `enhancement`.
  * Explain the use case and how it benefits other developers using the SDPS pattern.

### 3\. Pull Requests (PRs) 🚀

Ready to contribute code? Follow these steps:

1.  **Fork** the repository.
2.  Create a **Branch** for your feature or fix (`git checkout -b feature/amazing-feature`).
3.  Commit your changes with clear messages (e.g., `fix: adjust scaling for xxhdpi tablets`).
4.  **Push** to the branch (`git push origin feature/amazing-feature`).
5.  Open a **Pull Request**.

## Technical Guidelines

  * **Code Style:** Follow standard Kotlin/Compose coding conventions.
  * **Documentation:** If you add a new scaling method or parameter, please update the README or add KDoc comments.
  * **Precision:** Since this library focuses on UI precision, ensure that any changes to dimension calculations are tested across multiple screen configurations.

## License

By contributing, you agree that your contributions will be licensed under the project's **Apache License 2.0**.
