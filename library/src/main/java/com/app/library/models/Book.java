package com.app.library.models;

// --- ส่วนที่เพิ่มเข้ามาสำหรับ Lab 5 (JPA) ---
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// ---------------------------------------

@Entity // บอกให้ Spring สร้างตารางชื่อ Book ใน Database
public class Book {

    @Id // กำหนดให้เป็น Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ให้ DB รันเลข ID ให้เองอัตโนมัติ
    private Long id;

    private String title;
    private String author;
    private int publicationYear;
    private String genre;
    private int availableCopies;

    // Default constructor (จำเป็นต้องมีสำหรับ JPA)
    public Book() {}

    // Parameterized constructor (โค้ดเดิมของคุณ)
    public Book(String title, String author, int publicationYear, String genre, int availableCopies) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.availableCopies = availableCopies;
    }

    // --- Getters and Setters (โค้ดเดิมของคุณ) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    // toString (โค้ดเดิมของคุณ)
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", genre='" + genre + '\'' +
                ", availableCopies=" + availableCopies +
                '}';
    }
}