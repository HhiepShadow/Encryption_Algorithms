# Encryption Algorithms

This repository contains implementations of various encryption algorithms in Java. Encryption algorithms are essential tools for securing data and ensuring privacy in communication systems.

## Algorithms Included

- **AES (Advanced Encryption Standard)**: A symmetric encryption algorithm widely used for securing sensitive data.
- **RSA (Rivest-Shamir-Adleman)**: An asymmetric encryption algorithm commonly used for secure data transmission and digital signatures.
- **DES (Data Encryption Standard)**: A symmetric-key algorithm for encryption of electronic data.
- **Diffie-Hellman Key Exchange**: An algorithm for secure exchange of cryptographic keys over a public channel.

### Classic Encryption Algorithms
- **Caesar Cipher**: One of the earliest known substitution ciphers, where each letter in the plaintext is shifted a certain number of places down or up the alphabet.
- **Vigenère Cipher**: A method of encrypting alphabetic text by using a simple form of polyalphabetic substitution.
- **Playfair Cipher**: A symmetric encryption technique that employs a 5x5 grid of letters for encryption and decryption.
- **Hill Cipher**: A polygraphic substitution cipher based on linear algebra, where each letter is represented by a number and encrypted using matrix multiplication.
- **Line Shift Cipher**: The Line Shift Cipher, also known as the Caesar Cipher, is one of the simplest and oldest encryption techniques. It involves shifting each letter in the plaintext by a fixed number of positions down or up the alphabet.
- **Rail Fence Cipher**: The Rail Fence Cipher is a transposition cipher where the plaintext is written diagonally on consecutive "rails" of an imaginary fence, and then read off row by row to create the ciphertext. It provides a basic form of encryption by rearranging the order of letters.
- **Single Character Table**: The Single Character Table encryption technique involves replacing each character in the plaintext with a corresponding character from a predefined table. This table could be generated based on a key or a predetermined mapping scheme.
- **Permutation Cipher**: The Permutation Cipher, also known as the Columnar Transposition Cipher, rearranges the order of letters in the plaintext based on a permutation key. The plaintext is written into a grid, and the columns are then reordered according to the key to create the ciphertext.
## Usage

Each algorithm has its implementation in a separate directory along with documentation on how to use it. Refer to the README files within each directory for detailed instructions on installation and usage.

## Contributions

Contributions to this repository are welcome! If you have an implementation of an encryption algorithm in a programming language not already included, feel free to open a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
