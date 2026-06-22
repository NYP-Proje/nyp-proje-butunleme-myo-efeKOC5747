[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=24138560&assignment_repo_type=AssignmentRepo)
# Kişisel Finans Yönetimi (Final Projesi)

Bu proje, kullanıcıların gelir ve giderlerini takip etmelerini sağlayan, **MySQL (XAMPP)** veritabanı destekli, modern bir Java Swing masaüstü uygulamasıdır.

## 🚀 Proje Hakkında
Kullanıcıların harcamalarını kategorize edebileceği, anlık bakiye takibi yapabileceği ve finansal durumlarını görselleştirebileceği kapsamlı bir finans asistanıdır.

## 🛠️ Nasıl Çalışır? (Teknik İşleyiş)

Uygulamanın çalışma mantığı üç ana sütun üzerine kuruludur:

### 1. Veritabanı ve Başlatma Süreci
* **Otomatik Kurulum:** Uygulama çalıştırıldığında `veritabaniHazirla()` metodu devreye girer. Eğer sisteminizde `kisisel_finans` adında bir veritabanı yoksa, bunu otomatik olarak MySQL üzerinde oluşturur.
* **Tablo Yapısı:** `islemler` adında bir tablo oluşturur; bu tablo `id`, `tip` (Gelir/Gider), `kategori`, `aciklama` ve `tutar` verilerini tutar.
* **Başlangıç Verileri:** Tablo ilk kez oluşturulduğunda sistem boş kalmasın diye otomatik olarak örnek gelir ve gider verileri veritabanına eklenir.

### 2. Arayüz ve Veri Akışı
* **Verileri Yükle:** `verileriYukle()` metodu, veritabanındaki tüm kayıtları çeker ve uygulamanın orta kısmındaki `JTable` üzerinde listeler.
* **Dinamik Hesaplama:** Veriler çekilirken bir yandan `toplamGelir` ve `toplamGider` değişkenleri hesaplanır. Bu sayede uygulamanın sağ üst köşesindeki "Gelir Kartı", "Gider Kartı" ve "Net Bakiye" kartları anlık olarak güncellenir.
* **Kullanıcı Girişi:** Kullanıcı form üzerinden yeni bir işlem eklediğinde, veriler `PreparedStatement` kullanılarak güvenli bir şekilde veritabanına kaydedilir ve ardından arayüz otomatik olarak tazelenir.

### 3. Görselleştirme
* **Grafik Paneli:** `paintComponent` metodu kullanılarak özelleştirilmiş bir çizim alanı oluşturulmuştur. Bu kısım, veritabanındaki verilerin yüzde dağılımını temsil eden grafiksel bir özet sunar.

## 📦 Kurulum ve Çalıştırma

1. **XAMPP'ı Başlatın:** XAMPP kontrol panelinden **Apache** ve **MySQL** servislerini çalıştırın.
2. **Kütüphaneler:** Proje içerisinde `mysql-connector-j` kütüphanesinin projeye dahil edildiğinden emin olun.
3. **Çalıştırma:** `main` metodunu içeren `KisiselFinans_100` sınıfını çalıştırın.

## 💡 Kullanılan Teknolojiler
* **Dil:** Java (Swing)
* **Veritabanı:** MySQL
* **Bağlantı:** JDBC
* **Tasarım:** GridBagLayout & Swing bileşenleri

## 👤 İletişim
Bu proje, final ödevi kapsamında geliştirilmiştir.