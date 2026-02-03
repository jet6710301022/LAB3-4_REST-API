package com.app.library.services;

import com.app.library.models.Book;
import com.app.library.models.Member;
import com.app.library.models.BorrowingRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    // ---------- Books ----------
    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(Long id) {
        return null;
    }

    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    public Book updateBook(Book book) {
        return book;
    }

    public void deleteBook(Long id) {
    }

    // ---------- Members ----------
    public List<Member> getAllMembers() {
        return members;
    }

    public Member getMemberById(Long id) {
        return null;
    }

    public Member addMember(Member member) {
        members.add(member);
        return member;
    }

    public Member updateMember(Member member) {
        return member;
    }

    public void deleteMember(Long id) {
    }

    // ---------- Borrowing ----------
    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecords;
    }

    public BorrowingRecord borrowBook(BorrowingRecord record) {
        borrowingRecords.add(record);
        return record;
    }

    public BorrowingRecord returnBook(Long recordId, LocalDate returnDate) {
        return null;
    }
}
 