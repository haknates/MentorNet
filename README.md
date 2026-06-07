# MentorNET - Esenler Belediyesi Gençlik Network Platformu

MentorNET, Esenler Belediyesi bünyesindeki gençlerin birbirleriyle tanışmasını, projelerine ortak bulmasını ve uzman mentörlerden rehberlik almasını sağlayan modern bir mobil platformdur. Uygulama, yapay zeka destekli eşleştirme algoritmaları ve yerel veri yönetimi ile güvenli bir ekosistem sunar.

## 🚀 Temel Özellikler

*   **Yapay Zeka Destekli Eşleştirme:** Kullanıcıların ilgi alanlarını, eğitim bilgilerini ve gelecek hedeflerini analiz ederek en uygun mentör veya proje ortağıyla eşleştirme yapar.
*   **Gelişmiş Profil Yönetimi:** Üniversite, bölüm, yetkinlikler ve belgeler (Öğrenci belgesi, CV vb.) ile detaylı profil oluşturma.
*   **Network & Mentörlük:** Mevcut bağlantılarla doğrudan mesajlaşma ve mentörlerle görüşme planlama sistemi.
*   **Proje Ortağı Bul:** Yarışma (Teknofest, Tübitak vb.) veya girişim projeleri için ekip kurma veya var olan ekiplere katılma.
*   **Etkinlik & Takvim:** Belediye etkinliklerini takip etme ve kişisel görüşme takvimini (Mentör/Network seansları) yönetme.
*   **Esenlik Puan Sistemi:** Uygulama içi aktivitelerle puan kazanma ve bu puanları ödül kataloğunda kullanma.
*   **Modern Arayüz:** Karanlık mod desteği ve İngilizce dil seçeneği ile kullanıcı dostu deneyim.
*   **Güvenlik:** Tüm kullanıcılar belgeleriyle doğrulanmış kişilerden oluşur; 18 yaş altı kullanıcılar için ebeveyn denetimi mevcuttur.

## 🛠️ Teknik Altyapı

*   **Dil:** Kotlin
*   **Arayüz:** Jetpack Compose (Modern Declarative UI)
*   **Veritabanı:** SQLite (DatabaseHelper ile yerel veri yönetimi)
*   **Navigasyon:** Jetpack Navigation Compose (Animasyonlu geçişler ile)
*   **Mimari:** MVVM (Model-View-ViewModel)
*   **Tasarım:** Material Design 3

## 💻 Kurulum ve Çalıştırma

Projeyi yerel bilgisayarınızda çalıştırmak için şu adımları izleyin:

1.  **Projeyi Klonlayın:**
    ```bash
    git clone https://github.com/kullanici_adiniz/repository_adiniz.git
    ```
2.  **Android Studio'yu Açın:** Projeyi Android Studio (Ladybug veya üstü tavsiye edilir) ile açın.
3.  **Gradle Sync:** `File > Sync Project with Gradle Files` diyerek gerekli kütüphanelerin yüklenmesini bekleyin.
4.  **Logolar ve Görseller:** `app/src/main/res/drawable/` klasöründe `app_logo.png` ve `esenlink_bg.png` dosyalarının olduğundan emin olun.
5.  **Çalıştır:** Bir emülatör veya gerçek bir Android cihaz bağlayarak `Run` butonuna basın.

## 📱 APK İndir

Uygulamayı doğrudan Android cihazınıza indirip denemek için aşağıdaki bağlantıyı kullanabilirsiniz:

> [**MentorNET v1.0 - Google Drive İndirme Linki**](https://drive.google.com/file/d/1_JF1KKEo8raCgowyW_QHDxAf16tlLYz1/view?usp=sharing)

---
*Bu proje Esenler Belediyesi Gençlik Platformu vizyonuyla geliştirilmiştir.*
