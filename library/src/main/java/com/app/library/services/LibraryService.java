package com.app.library.services;

import com.app.library.models.Book;
import com.app.library.models.Member;
import com.app.library.models.BorrowingRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // เพิ่ม Import สำหรับการกรองข้อมูล

@Service
public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    // ---------- Books (โค้ดเดิมของคุณ) ----------
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

    // ---------- Members (โค้ดเดิมของคุณ) ----------
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

    // ---------- Borrowing (โค้ดเดิมของคุณ) ----------
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

    // ==========================================
    // ส่วนที่เพิ่มใหม่สำหรับ Lab 4 (ไม่ลบของเก่า)
    // ==========================================

    // 1. ค้นหาหนังสือตามประเภท (Genre)
    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    // 2. ค้นหาตามผู้แต่ง และกรองประเภทเพิ่มได้ (Author & Optional Genre)
    public List<Book> getBooksByAuthorAndGenre(String author, String genre) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> genre == null || book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    // 3. ค้นหาหนังสือที่ต้องคืนตามวันที่กำหนด (Due Date)
    public List<BorrowingRecord> getRecordsByDueDate(LocalDate dueDate) {
        return borrowingRecords.stream()
                .filter(record -> record.getDueDate().equals(dueDate))
                .collect(Collectors.toList());
    }

    // 4. ตรวจสอบวันที่หนังสือจะว่าง (Availability Date)
    public LocalDate getBookAvailableDate(Long bookId) {
        // กรองหา record ของหนังสือเล่มนี้ที่ยังไม่คืน และหาวันที่คืน (due date) ที่เร็วที่สุด
        return borrowingRecords.stream()
                .filter(record -> record.getBookId().equals(bookId))
                .map(BorrowingRecord::getDueDate)
                .sorted()
                .findFirst()
                .orElse(LocalDate.now()); // ถ้าไม่มีคนยืม ก็คือว่างวันนี้
    }
}