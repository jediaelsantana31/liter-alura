package com.challenge.literalura.main;

import com.challenge.literalura.model.Author;
import com.challenge.literalura.model.Book;
import com.challenge.literalura.model.Languages;
import com.challenge.literalura.repository.BookRepository;
import com.challenge.literalura.service.APIRequestService;
import com.challenge.literalura.service.DataConverter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class BookManagementApp {

    private final Scanner scanner = new Scanner(System.in);
    private final APIRequestService apiRequestService;
    private final DataConverter dataConverter;
    private final BookRepository bookRepository;

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    """;
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listAuthorsAliveInYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private String getUserInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    private void searchBookByTitle() {
        String bookTitle = getUserInput("Digite o título do livro que deseja buscar:");
        String json = apiRequestService.getData("https://gutendex.com/books/?search=%20" + bookTitle.replace(" ", "+"));
        List<Book> books = dataConverter.convert(json, List.class); // Corrigido para List<Book>

        if (books.isEmpty()) {
            System.out.println("\nLivro não encontrado.\n");
        } else {
            books.forEach(bookRepository::save);
            books.forEach(System.out::println);
        }
    }

    private void listRegisteredBooks() {
        List<Book> storedBooks = bookRepository.findAll();
        storedBooks.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .forEach(System.out::println);
    }

    private void listRegisteredAuthors() {
        List<Author> authors = bookRepository.getAuthorsInfo(); // Corrigido para getAuthorsInfo()
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void listAuthorsAliveInYear() {
        System.out.println("Digite o ano a partir do qual deseja saber os autores vivos:");
        int year = scanner.nextInt();
        scanner.nextLine();

        List<Author> authors = bookRepository.getAuthorsAliveAfter(year); // Corrigido para getAuthorsAliveAfter(year)
        authors.stream()
                .sorted(Comparator.comparing(Author::getName))
                .forEach(System.out::println);
    }

    private void listBooksByLanguage() {
        System.out.println("""
                Escolha entre as opções de idioma para buscar livros:

                en - Inglês
                es - Espanhol
                fr - Francês
                it - Italiano
                pt - Português
                """);
        String languageCode = scanner.nextLine().trim().toLowerCase();

        Languages language = Languages.fromString(languageCode);
        List<Book> books = bookRepository.findByLanguages(language); // Corrigido para findByLanguages(language)
        books.forEach(System.out::println);
    }
}
