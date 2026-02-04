package com.app.library.controllers;

import com.app.library.models.Book;
import com.app.library.models.Member;
import com.app.library.models.BorrowingRecord;
import com.app.library.services.LibraryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat; // เพิ่มสำหรับจัดการวันที่
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private LibraryService libraryService;

    // ==================== Book Endpoints (เดิม) ====================

    @GetMapping("/books")
    public ResponseEntity<Collection<Book>> getAllBooks() {
        Collection<Book> books = libraryService.getAllBooks();
        logger.info("The list of books returned" + books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = libraryService.getBookById(id);
        logger.info("The book returned" + book);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        logger.info("The book was added");
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (libraryService.getBookById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedBook.setId(id);
        libraryService.updateBook(updatedBook);
        logger.info("The book has been updated " + updatedBook);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (libraryService.getBookById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        libraryService.deleteBook(id);
        logger.info("The book has been deleted ");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ==================== Member Endpoints (เดิม) ====================

    @GetMapping("/members")
    public ResponseEntity<Collection<Member>> getAllMembers() {
        Collection<Member> members = libraryService.getAllMembers();
        logger.info("The members in the system " + members);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = libraryService.getMemberById(id);
        logger.info("The member you retrieved " + member);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/members")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        libraryService.addMember(member);
        logger.info("The member has been added ");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        if (libraryService.getMemberById(id) == null) { // แก้ไขเงื่อนไขจากเดิมเล็กน้อยเพื่อให้ถูกต้อง
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedMember.setId(id);
        libraryService.updateMember(updatedMember);
        logger.info("The member has been updated " + updatedMember);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (libraryService.getMemberById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        libraryService.deleteMember(id);
        logger.info("The member has been deleted " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ==================== BorrowingRecord Endpoints (เดิม) ====================

    @GetMapping("/borrowing-records")
    public ResponseEntity<Collection<BorrowingRecord>> getAllBorrowingRecords() {
        Collection<BorrowingRecord> records = libraryService.getAllBorrowingRecords();
        logger.info("The records has been retrieved " + records);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowingRecord> borrowBook(@RequestBody BorrowingRecord record) {
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        libraryService.borrowBook(record);
        logger.info("The book has been borrowed " + record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PutMapping("/return/{recordId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long recordId) {
        libraryService.returnBook(recordId, LocalDate.now());
        logger.info("The book has been retrieved " + recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // ==================== ส่วนที่เพิ่มใหม่สำหรับ Lab 4 ====================

    // 1. ค้นหาหนังสือตามประเภท (Genre) - ใช้ Query Parameter
    // URL: GET http://localhost:8080/api/books/genre?genre=Fiction
    @GetMapping("/books/genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam String genre) {
        List<Book> books = libraryService.getBooksByGenre(genre);
        logger.info("Books filtered by genre {}: {}", genre, books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // 2. ค้นหาตามผู้แต่ง (Path Variable) และระบุประเภทได้ (Query Parameter)
    // URL: GET http://localhost:8080/api/books/author/F. Scott Fitzgerald?genre=Fiction
    @GetMapping("/books/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthorAndGenre(
            @PathVariable String author,
            @RequestParam(required = false) String genre) {
        List<Book> books = libraryService.getBooksByAuthorAndGenre(author, genre);
        logger.info("Books by author {} and genre {}: {}", author, genre, books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // 3. ค้นหาหนังสือที่ต้องคืนในวันที่ระบุ
    // URL: GET http://localhost:8080/api/books/dueondate?dueDate=2024-03-25
    @GetMapping("/books/dueondate")
    public ResponseEntity<List<BorrowingRecord>> getRecordsByDueDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        List<BorrowingRecord> records = libraryService.getRecordsByDueDate(dueDate);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    // 4. เช็กว่าหนังสือ ID นี้จะว่างอีกทีเมื่อไหร่
    // URL: GET http://localhost:8080/api/bookavailabileDate?bookId=1
    @GetMapping("/bookavailabileDate")
    public ResponseEntity<LocalDate> getBookAvailableDate(@RequestParam Long bookId) {
        LocalDate date = libraryService.getBookAvailableDate(bookId);
        return new ResponseEntity<>(date, HttpStatus.OK);
    }
}