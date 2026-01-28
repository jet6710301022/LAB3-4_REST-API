package com.app.library.services;

import com.app.library.models.Book;
import com.app.library.models.Member;
import com.app.library.models.BorrowingRecord;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private Map<Long, Book> books = new HashMap<Long, Book>();
    private Map<Long, Member> members = new HashMap<Long, Member>();
    private Map<Long, BorrowingRecord> borrowingRecords = new HashMap<Long, BorrowingRecord>();

    // ==================== [เพิ่มส่วนนี้] Constructor เพื่อให้รันแล้วมีข้อมูลโชว์ทันที ====================
    public LibraryService() {
        // ID: 1, ชื่อ: Java Beginner, คนแต่ง: Jet, ปี: 2024, หมวด: Education, จำนวน: 5
        books.put(1L, new Book(1L, "Java Beginner", "Jet", 2024, "Education", 5));
    }

    // ==================== Book Methods ====================

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public Book getBookById(Long id) {
        return books.get(id);
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void updateBook(Book updatedBook) {
        books.put(updatedBook.getId(), updatedBook);
    }

    public void deleteBook(Long id) {
        books.remove(id);
    }

    // ==================== Member Methods ====================

    public Collection<Member> getAllMembers() {
        return members.values();
    }

    public Member getMemberById(Long id) {
        return members.get(id);
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public void updateMember(Member updatedMember) {
        members.put(updatedMember.getId(), updatedMember);
    }

    public void deleteMember(Long id) {
        members.remove(id);
    }

    // ==================== BorrowingRecord Methods ====================

    public Collection<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecords.values();
    }

    public void borrowBook(BorrowingRecord record) {
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecords.put(record.getId(), record);
        
        Book book = books.get(record.getBookId());
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
        }
    }

    public void returnBook(Long recordId, LocalDate returnDate) {
        BorrowingRecord record = borrowingRecords.get(recordId);
        if (record != null) {
            Book book = books.get(record.getBookId());
            if (book != null) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
            }
        }
    }

    // ==================== Lab 4: Search Methods ====================

    public List<Book> getBooksByGenre(String genre) {
        return books.values().stream()
                .filter(book -> book.getGenre() != null && book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor() != null && book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<BorrowingRecord> getBooksDueOnDate(LocalDate dueDate) {
        return borrowingRecords.values().stream()
                .filter(record -> record.getDueDate() != null && record.getDueDate().equals(dueDate))
                .collect(Collectors.toList());
    }
}