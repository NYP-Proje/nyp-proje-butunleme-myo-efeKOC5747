//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class KisiselFinans_100 extends JFrame {
    private final Color RENK_ARKA = new Color(245, 247, 250);
    private final Color RENK_PANEL;
    private final Color RENK_KOYU;
    private final Color RENK_HAFIF;
    private final Color RENK_YESIL;
    private final Color RENK_KIRMIZI;
    private final Color RENK_MAVI;
    private DefaultTableModel model;
    private JLabel lblGelirKart;
    private JLabel lblGiderKart;
    private JLabel lblBakiyeKart;
    private double toplamGelir;
    private double toplamGider;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;

    public KisiselFinans_100() {
        this.RENK_PANEL = Color.WHITE;
        this.RENK_KOYU = new Color(33, 37, 41);
        this.RENK_HAFIF = new Color(108, 117, 125);
        this.RENK_YESIL = new Color(40, 167, 69);
        this.RENK_KIRMIZI = new Color(220, 53, 69);
        this.RENK_MAVI = new Color(0, 123, 255);
        this.toplamGelir = (double)0.0F;
        this.toplamGider = (double)0.0F;
        this.DB_URL = "jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC";
        this.DB_USER = "root";
        this.DB_PASS = "";
        this.setTitle("Kisisel Finans Yonetimi - %100 Final");
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.getContentPane().setBackground(this.RENK_ARKA);
        this.setLayout(new BorderLayout(15, 15));
        this.add(this.ustPanelOlustur(), "North");
        this.add(this.ortaPanelOlustur(), "Center");
        this.add(this.altPanelOlustur(), "South");
        this.veritabaniHazirla();
        this.verileriYukle();
    }

    private void veritabaniHazirla() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC", "root", "");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS kisisel_finans");
            stmt.close();
            conn.close();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC", "root", "");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS islemler (id INT AUTO_INCREMENT PRIMARY KEY,tip VARCHAR(10),kategori VARCHAR(50),aciklama VARCHAR(255),tutar DOUBLE)";
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS satir_sayisi FROM islemler");
            if (rs.next() && rs.getInt("satir_sayisi") == 0) {
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gelir', 'Maas/Gelir', 'Aylik Maas Odemesi', 15000.00)");
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gider', 'Kira/Ev', 'Ev Kirasi', 3000.00)");
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gider', 'Gida/Market', 'Haftalik Market', 750.00)");
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gider', 'Fatura', 'Elektrik & Internet', 500.00)");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "MySQL baglantisi kurulamadi!\nLutfen XAMPP uzerinden Apache ve MySQL servislerinin calistigindan emin olun.", "Veritabani Hatasi", 0);
        }

    }

    private void verileriYukle() {
        if (this.model != null) {
            this.model.setRowCount(0);
            this.toplamGelir = (double)0.0F;
            this.toplamGider = (double)0.0F;

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC", "root", "");
                Statement stmt = conn.createStatement();

                ResultSet rs;
                String tip;
                String kategori;
                String aciklama;
                double tutar;
                String simge;
                for(rs = stmt.executeQuery("SELECT * FROM islemler"); rs.next(); this.model.addRow(new Object[]{tip, kategori, aciklama, simge + String.format("%,.2f TL", tutar)})) {
                    tip = rs.getString("tip");
                    kategori = rs.getString("kategori");
                    aciklama = rs.getString("aciklama");
                    tutar = rs.getDouble("tutar");
                    simge = tip.equalsIgnoreCase("Gelir") ? "+" : "-";
                    if (tip.equalsIgnoreCase("Gelir")) {
                        this.toplamGelir += tutar;
                    } else {
                        this.toplamGider += tutar;
                    }
                }

                this.lblGelirKart.setText(String.format("%,.2f TL", this.toplamGelir));
                this.lblGiderKart.setText(String.format("%,.2f TL", this.toplamGider));
                this.lblBakiyeKart.setText(String.format("%,.2f TL", this.toplamGelir - this.toplamGider));
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private JPanel ustPanelOlustur() {
        JPanel ustPanel = new JPanel(new BorderLayout(10, 10));
        ustPanel.setBackground(this.RENK_ARKA);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        JLabel lblBaslik = new JLabel("\ud83d\udcca Butcem - Kisisel Finans Asistani");
        lblBaslik.setFont(new Font("Segoe UI", 1, 22));
        lblBaslik.setForeground(this.RENK_KOYU);
        ustPanel.add(lblBaslik, "West");
        JPanel kartlarPaneli = new JPanel(new FlowLayout(2, 15, 0));
        kartlarPaneli.setBackground(this.RENK_ARKA);
        this.lblGelirKart = new JLabel("0.00 TL");
        this.lblGiderKart = new JLabel("0.00 TL");
        this.lblBakiyeKart = new JLabel("0.00 TL");
        kartlarPaneli.add(this.kartOlustur("Toplam Gelir", this.lblGelirKart, this.RENK_YESIL));
        kartlarPaneli.add(this.kartOlustur("Toplam Gider", this.lblGiderKart, this.RENK_KIRMIZI));
        kartlarPaneli.add(this.kartOlustur("Net Bakiye", this.lblBakiyeKart, this.RENK_MAVI));
        ustPanel.add(kartlarPaneli, "East");
        return ustPanel;
    }

    private JPanel kartOlustur(String baslik, JLabel lblDeger, Color degerRengi) {
        JPanel kart = new JPanel();
        kart.setLayout(new BoxLayout(kart, 1));
        kart.setBackground(this.RENK_PANEL);
        kart.setPreferredSize(new Dimension(150, 60));
        kart.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel lblKartBaslik = new JLabel(baslik);
        lblKartBaslik.setFont(new Font("Segoe UI", 0, 11));
        lblKartBaslik.setForeground(this.RENK_HAFIF);
        lblDeger.setFont(new Font("Segoe UI", 1, 15));
        lblDeger.setForeground(degerRengi);
        kart.add(lblKartBaslik);
        kart.add(Box.createRigidArea(new Dimension(0, 4)));
        kart.add(lblDeger);
        return kart;
    }

    private JPanel ortaPanelOlustur() {
        JPanel anaOrtaPanel = new JPanel(new GridBagLayout());
        anaOrtaPanel.setBackground(this.RENK_ARKA);
        anaOrtaPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 1;
        gbc.weighty = (double)1.0F;
        gbc.gridx = 0;
        gbc.weightx = (double)0.25F;
        gbc.insets = new Insets(0, 0, 0, 10);
        anaOrtaPanel.add(this.formPanelOlustur(), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.45;
        gbc.insets = new Insets(0, 0, 0, 10);
        anaOrtaPanel.add(this.tabloPanelOlustur(), gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(0, 0, 0, 0);
        anaOrtaPanel.add(this.grafikPanelOlustur(), gbc);
        return anaOrtaPanel;
    }

    private JPanel formPanelOlustur() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(this.RENK_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(5, 0, 12, 0);
        gbc.gridx = 0;
        JLabel lblFormBaslik = new JLabel("Yeni Islem Girisi");
        lblFormBaslik.setFont(new Font("Segoe UI", 1, 16));
        lblFormBaslik.setForeground(this.RENK_KOYU);
        gbc.gridy = 0;
        panel.add(lblFormBaslik, gbc);
        gbc.gridy = 1;
        panel.add(new JLabel("Islem Tipi:"), gbc);
        final JComboBox<String> cmbTip = new JComboBox(new String[]{"Gelir (+)", "Gider (-)"});
        gbc.gridy = 2;
        panel.add(cmbTip, gbc);
        gbc.gridy = 3;
        panel.add(new JLabel("Kategori:"), gbc);
        final JComboBox<String> cmbKategori = new JComboBox(new String[]{"Maas/Gelir", "Kira/Ev", "Gida/Market", "Fatura", "Eglence", "Diger"});
        gbc.gridy = 4;
        panel.add(cmbKategori, gbc);
        gbc.gridy = 5;
        panel.add(new JLabel("Tutar (TL):"), gbc);
        final JTextField txtTutar = new JTextField("0.00");
        gbc.gridy = 6;
        panel.add(txtTutar, gbc);
        gbc.gridy = 7;
        panel.add(new JLabel("Aciklama:"), gbc);
        final JTextField txtAciklama = new JTextField();
        gbc.gridy = 8;
        panel.add(txtAciklama, gbc);
        gbc.weighty = (double)1.0F;
        gbc.gridy = 9;
        panel.add(Box.createGlue(), gbc);
        JButton btnKaydet = new JButton("Islemi Kaydet");
        btnKaydet.setBackground(this.RENK_MAVI);
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Segoe UI", 1, 13));
        gbc.weighty = (double)0.0F;
        gbc.gridy = 10;
        panel.add(btnKaydet, gbc);
        btnKaydet.addActionListener(new ActionListener() {
            {
                Objects.requireNonNull(KisiselFinans_100.this);
            }

            public void actionPerformed(ActionEvent e) {
                String aciklama = txtAciklama.getText().trim();
                String tutarStr = txtTutar.getText().trim();
                if (aciklama.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Lütfen bir açıklama giriniz!", "Eksik Bilgi", 2);
                } else {
                    try {
                        double tutar = Double.parseDouble(tutarStr);
                        if (tutar <= (double)0.0F) {
                            throw new NumberFormatException();
                        }

                        String tipSecim = cmbTip.getSelectedItem().toString();
                        String dbTip = tipSecim.contains("Gelir") ? "Gelir" : "Gider";
                        String kategori = cmbKategori.getSelectedItem().toString();
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC", "root", "");
                        String sql = "INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES (?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, dbTip);
                        pstmt.setString(2, kategori);
                        pstmt.setString(3, aciklama);
                        pstmt.setDouble(4, tutar);
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();
                        KisiselFinans_100.this.verileriYukle();
                        txtAciklama.setText("");
                        txtTutar.setText("0.00");
                    } catch (NumberFormatException var12) {
                        JOptionPane.showMessageDialog(panel, "Geçersiz tutar! Lütfen sadece sayı giriniz.", "Hatalı Giriş", 0);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Veritabanına kaydedilirken hata olustu!", "Hata", 0);
                    }

                }
            }
        });
        return panel;
    }

    private JPanel tabloPanelOlustur() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(this.RENK_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        JLabel lblTabloBaslik = new JLabel("Son Islemler");
        lblTabloBaslik.setFont(new Font("Segoe UI", 1, 16));
        lblTabloBaslik.setForeground(this.RENK_KOYU);
        panel.add(lblTabloBaslik, "North");
        String[] sutunlar = new String[]{"Tip", "Kategori", "Aciklama", "Tutar"};
        this.model = new DefaultTableModel((Object[][])null, sutunlar) {
            {
                Objects.requireNonNull(KisiselFinans_100.this);
            }

            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable tablo = new JTable(this.model);
        tablo.setFont(new Font("Segoe UI", 0, 12));
        tablo.setRowHeight(25);
        tablo.setShowGrid(false);
        panel.add(new JScrollPane(tablo), "Center");
        return panel;
    }

    private JPanel grafikPanelOlustur() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(this.RENK_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        JLabel lblGrafikBaslik = new JLabel("Gider Dagilimi (%)");
        lblGrafikBaslik.setFont(new Font("Segoe UI", 1, 16));
        lblGrafikBaslik.setForeground(this.RENK_KOYU);
        panel.add(lblGrafikBaslik, "North");
        JPanel grafikCizimPaneli = new JPanel() {
            {
                Objects.requireNonNull(KisiselFinans_100.this);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cap = Math.min(this.getWidth(), this.getHeight()) - 100;
                int x = (this.getWidth() - cap) / 2;
                int y = (this.getHeight() - cap) / 2 - 20;
                int[] acilar = new int[]{252, 65, 43};
                Color[] renkler = new Color[]{new Color(255, 193, 7), new Color(23, 162, 184), new Color(111, 66, 193)};
                int baslangicAcisi = 0;

                for(int i = 0; i < acilar.length; ++i) {
                    g2d.setColor(renkler[i]);
                    g2d.fillArc(x, y, cap, cap, baslangicAcisi, acilar[i]);
                    baslangicAcisi += acilar[i];
                }

                g2d.setColor(Color.WHITE);
                g2d.fillOval(x + (cap - cap / 2) / 2, y + (cap - cap / 2) / 2, cap / 2, cap / 2);
                int legendY = this.getHeight() - 60;
                g2d.setFont(new Font("Segoe UI", 0, 11));
                g2d.setColor(new Color(255, 193, 7));
                g2d.fillRect(20, legendY, 12, 12);
                g2d.setColor(KisiselFinans_100.this.RENK_KOYU);
                g2d.drawString("Kira (%70)", 38, legendY + 10);
                g2d.setColor(new Color(23, 162, 184));
                g2d.fillRect(120, legendY, 12, 12);
                g2d.setColor(KisiselFinans_100.this.RENK_KOYU);
                g2d.drawString("Gida (%18)", 138, legendY + 10);
                g2d.setColor(new Color(111, 66, 193));
                g2d.fillRect(220, legendY, 12, 12);
                g2d.setColor(KisiselFinans_100.this.RENK_KOYU);
                g2d.drawString("Fatura (%12)", 238, legendY + 10);
            }
        };
        grafikCizimPaneli.setBackground(this.RENK_PANEL);
        panel.add(grafikCizimPaneli, "Center");
        return panel;
    }

    private JPanel altPanelOlustur() {
        JPanel altPanel = new JPanel(new FlowLayout(1));
        altPanel.setBackground(this.RENK_ARKA);
        JLabel lblFooter = new JLabel("© 2026 Kisisel Finans Asistani | Asama 100: MySQL Veritabani Baglantisi");
        lblFooter.setFont(new Font("Segoe UI", 0, 11));
        lblFooter.setForeground(this.RENK_HAFIF);
        altPanel.add(lblFooter);
        return altPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> (new KisiselFinans_100()).setVisible(true));
    }
}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class KisiselFinans_100 extends JFrame {
    private final Color RENK_ARKA = new Color(245, 247, 250);
    private final Color RENK_PANEL;
    private final Color RENK_KOYU;
    private final Color RENK_HAFIF;
    private final Color RENK_YESIL;
    private final Color RENK_KIRMIZI;
    private final Color RENK_MAVI;
    private DefaultTableModel model;
    private JLabel lblGelirKart;
    private JLabel lblGiderKart;
    private JLabel lblBakiyeKart;
    private double toplamGelir;
    private double toplamGider;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;

    public KisiselFinans_100() {
        this.RENK_PANEL = Color.WHITE;
        this.RENK_KOYU = new Color(33, 37, 41);
        this.RENK_HAFIF = new Color(108, 117, 125);
        this.RENK_YESIL = new Color(40, 167, 69);
        this.RENK_KIRMIZI = new Color(220, 53, 69);
        this.RENK_MAVI = new Color(0, 123, 255);
        this.toplamGelir = (double)0.0F;
        this.toplamGider = (double)0.0F;
        this.DB_URL = "jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC";
        this.DB_USER = "root";
        this.DB_PASS = "";
        this.setTitle("Kisisel Finans Yonetimi - %100 Final");
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.getContentPane().setBackground(this.RENK_ARKA);
        this.setLayout(new BorderLayout(15, 15));
        this.add(this.ustPanelOlustur(), "North");
        this.add(this.ortaPanelOlustur(), "Center");
        this.add(this.altPanelOlustur(), "South");
        this.veritabaniHazirla();
        this.verileriYukle();
    }

    private void veritabaniHazirla() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC", "root", "");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS kisisel_finans");
            stmt.close();
            conn.close();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC", "root", "");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS islemler (id INT AUTO_INCREMENT PRIMARY KEY,tip VARCHAR(10),kategori VARCHAR(50),aciklama VARCHAR(255),tutar DOUBLE)";
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS satir_sayisi FROM islemler");
            if (rs.next() && rs.getInt("satir_sayisi") == 0) {
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gelir', 'Maas/Gelir', 'Aylik Maas Odemesi', 15000.00)");
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gider', 'Kira/Ev', 'Ev Kirasi', 3000.00)");
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gider', 'Gida/Market', 'Haftalik Market', 750.00)");
                stmt.executeUpdate("INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES ('Gider', 'Fatura', 'Elektrik & Internet', 500.00)");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "MySQL baglantisi kurulamadi!\nLutfen XAMPP uzerinden Apache ve MySQL servislerinin calistigindan emin olun.", "Veritabani Hatasi", 0);
        }

    }

    private void verileriYukle() {
        if (this.model != null) {
            this.model.setRowCount(0);
            this.toplamGelir = (double)0.0F;
            this.toplamGider = (double)0.0F;

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC", "root", "");
                Statement stmt = conn.createStatement();

                ResultSet rs;
                String tip;
                String kategori;
                String aciklama;
                double tutar;
                String simge;
                for(rs = stmt.executeQuery("SELECT * FROM islemler"); rs.next(); this.model.addRow(new Object[]{tip, kategori, aciklama, simge + String.format("%,.2f TL", tutar)})) {
                    tip = rs.getString("tip");
                    kategori = rs.getString("kategori");
                    aciklama = rs.getString("aciklama");
                    tutar = rs.getDouble("tutar");
                    simge = tip.equalsIgnoreCase("Gelir") ? "+" : "-";
                    if (tip.equalsIgnoreCase("Gelir")) {
                        this.toplamGelir += tutar;
                    } else {
                        this.toplamGider += tutar;
                    }
                }

                this.lblGelirKart.setText(String.format("%,.2f TL", this.toplamGelir));
                this.lblGiderKart.setText(String.format("%,.2f TL", this.toplamGider));
                this.lblBakiyeKart.setText(String.format("%,.2f TL", this.toplamGelir - this.toplamGider));
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private JPanel ustPanelOlustur() {
        JPanel ustPanel = new JPanel(new BorderLayout(10, 10));
        ustPanel.setBackground(this.RENK_ARKA);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        JLabel lblBaslik = new JLabel("\ud83d\udcca Butcem - Kisisel Finans Asistani");
        lblBaslik.setFont(new Font("Segoe UI", 1, 22));
        lblBaslik.setForeground(this.RENK_KOYU);
        ustPanel.add(lblBaslik, "West");
        JPanel kartlarPaneli = new JPanel(new FlowLayout(2, 15, 0));
        kartlarPaneli.setBackground(this.RENK_ARKA);
        this.lblGelirKart = new JLabel("0.00 TL");
        this.lblGiderKart = new JLabel("0.00 TL");
        this.lblBakiyeKart = new JLabel("0.00 TL");
        kartlarPaneli.add(this.kartOlustur("Toplam Gelir", this.lblGelirKart, this.RENK_YESIL));
        kartlarPaneli.add(this.kartOlustur("Toplam Gider", this.lblGiderKart, this.RENK_KIRMIZI));
        kartlarPaneli.add(this.kartOlustur("Net Bakiye", this.lblBakiyeKart, this.RENK_MAVI));
        ustPanel.add(kartlarPaneli, "East");
        return ustPanel;
    }

    private JPanel kartOlustur(String baslik, JLabel lblDeger, Color degerRengi) {
        JPanel kart = new JPanel();
        kart.setLayout(new BoxLayout(kart, 1));
        kart.setBackground(this.RENK_PANEL);
        kart.setPreferredSize(new Dimension(150, 60));
        kart.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel lblKartBaslik = new JLabel(baslik);
        lblKartBaslik.setFont(new Font("Segoe UI", 0, 11));
        lblKartBaslik.setForeground(this.RENK_HAFIF);
        lblDeger.setFont(new Font("Segoe UI", 1, 15));
        lblDeger.setForeground(degerRengi);
        kart.add(lblKartBaslik);
        kart.add(Box.createRigidArea(new Dimension(0, 4)));
        kart.add(lblDeger);
        return kart;
    }

    private JPanel ortaPanelOlustur() {
        JPanel anaOrtaPanel = new JPanel(new GridBagLayout());
        anaOrtaPanel.setBackground(this.RENK_ARKA);
        anaOrtaPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 1;
        gbc.weighty = (double)1.0F;
        gbc.gridx = 0;
        gbc.weightx = (double)0.25F;
        gbc.insets = new Insets(0, 0, 0, 10);
        anaOrtaPanel.add(this.formPanelOlustur(), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.45;
        gbc.insets = new Insets(0, 0, 0, 10);
        anaOrtaPanel.add(this.tabloPanelOlustur(), gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(0, 0, 0, 0);
        anaOrtaPanel.add(this.grafikPanelOlustur(), gbc);
        return anaOrtaPanel;
    }

    private JPanel formPanelOlustur() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(this.RENK_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(5, 0, 12, 0);
        gbc.gridx = 0;
        JLabel lblFormBaslik = new JLabel("Yeni Islem Girisi");
        lblFormBaslik.setFont(new Font("Segoe UI", 1, 16));
        lblFormBaslik.setForeground(this.RENK_KOYU);
        gbc.gridy = 0;
        panel.add(lblFormBaslik, gbc);
        gbc.gridy = 1;
        panel.add(new JLabel("Islem Tipi:"), gbc);
        final JComboBox<String> cmbTip = new JComboBox(new String[]{"Gelir (+)", "Gider (-)"});
        gbc.gridy = 2;
        panel.add(cmbTip, gbc);
        gbc.gridy = 3;
        panel.add(new JLabel("Kategori:"), gbc);
        final JComboBox<String> cmbKategori = new JComboBox(new String[]{"Maas/Gelir", "Kira/Ev", "Gida/Market", "Fatura", "Eglence", "Diger"});
        gbc.gridy = 4;
        panel.add(cmbKategori, gbc);
        gbc.gridy = 5;
        panel.add(new JLabel("Tutar (TL):"), gbc);
        final JTextField txtTutar = new JTextField("0.00");
        gbc.gridy = 6;
        panel.add(txtTutar, gbc);
        gbc.gridy = 7;
        panel.add(new JLabel("Aciklama:"), gbc);
        final JTextField txtAciklama = new JTextField();
        gbc.gridy = 8;
        panel.add(txtAciklama, gbc);
        gbc.weighty = (double)1.0F;
        gbc.gridy = 9;
        panel.add(Box.createGlue(), gbc);
        JButton btnKaydet = new JButton("Islemi Kaydet");
        btnKaydet.setBackground(this.RENK_MAVI);
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Segoe UI", 1, 13));
        gbc.weighty = (double)0.0F;
        gbc.gridy = 10;
        panel.add(btnKaydet, gbc);
        btnKaydet.addActionListener(new ActionListener() {
            {
                Objects.requireNonNull(KisiselFinans_100.this);
            }

            public void actionPerformed(ActionEvent e) {
                String aciklama = txtAciklama.getText().trim();
                String tutarStr = txtTutar.getText().trim();
                if (aciklama.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Lütfen bir açıklama giriniz!", "Eksik Bilgi", 2);
                } else {
                    try {
                        double tutar = Double.parseDouble(tutarStr);
                        if (tutar <= (double)0.0F) {
                            throw new NumberFormatException();
                        }

                        String tipSecim = cmbTip.getSelectedItem().toString();
                        String dbTip = tipSecim.contains("Gelir") ? "Gelir" : "Gider";
                        String kategori = cmbKategori.getSelectedItem().toString();
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kisisel_finans?useSSL=false&serverTimezone=UTC", "root", "");
                        String sql = "INSERT INTO islemler (tip, kategori, aciklama, tutar) VALUES (?, ?, ?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, dbTip);
                        pstmt.setString(2, kategori);
                        pstmt.setString(3, aciklama);
                        pstmt.setDouble(4, tutar);
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.close();
                        KisiselFinans_100.this.verileriYukle();
                        txtAciklama.setText("");
                        txtTutar.setText("0.00");
                    } catch (NumberFormatException var12) {
                        JOptionPane.showMessageDialog(panel, "Geçersiz tutar! Lütfen sadece sayı giriniz.", "Hatalı Giriş", 0);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Veritabanına kaydedilirken hata olustu!", "Hata", 0);
                    }

                }
            }
        });
        return panel;
    }

    private JPanel tabloPanelOlustur() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(this.RENK_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        JLabel lblTabloBaslik = new JLabel("Son Islemler");
        lblTabloBaslik.setFont(new Font("Segoe UI", 1, 16));
        lblTabloBaslik.setForeground(this.RENK_KOYU);
        panel.add(lblTabloBaslik, "North");
        String[] sutunlar = new String[]{"Tip", "Kategori", "Aciklama", "Tutar"};
        this.model = new DefaultTableModel((Object[][])null, sutunlar) {
            {
                Objects.requireNonNull(KisiselFinans_100.this);
            }

            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable tablo = new JTable(this.model);
        tablo.setFont(new Font("Segoe UI", 0, 12));
        tablo.setRowHeight(25);
        tablo.setShowGrid(false);
        panel.add(new JScrollPane(tablo), "Center");
        return panel;
    }

    private JPanel grafikPanelOlustur() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(this.RENK_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(222, 226, 230), 1, true), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        JLabel lblGrafikBaslik = new JLabel("Gider Dagilimi (%)");
        lblGrafikBaslik.setFont(new Font("Segoe UI", 1, 16));
        lblGrafikBaslik.setForeground(this.RENK_KOYU);
        panel.add(lblGrafikBaslik, "North");
        JPanel grafikCizimPaneli = new JPanel() {
            {
                Objects.requireNonNull(KisiselFinans_100.this);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cap = Math.min(this.getWidth(), this.getHeight()) - 100;
                int x = (this.getWidth() - cap) / 2;
                int y = (this.getHeight() - cap) / 2 - 20;
                int[] acilar = new int[]{252, 65, 43};
                Color[] renkler = new Color[]{new Color(255, 193, 7), new Color(23, 162, 184), new Color(111, 66, 193)};
                int baslangicAcisi = 0;

                for(int i = 0; i < acilar.length; ++i) {
                    g2d.setColor(renkler[i]);
                    g2d.fillArc(x, y, cap, cap, baslangicAcisi, acilar[i]);
                    baslangicAcisi += acilar[i];
                }

                g2d.setColor(Color.WHITE);
                g2d.fillOval(x + (cap - cap / 2) / 2, y + (cap - cap / 2) / 2, cap / 2, cap / 2);
                int legendY = this.getHeight() - 60;
                g2d.setFont(new Font("Segoe UI", 0, 11));
                g2d.setColor(new Color(255, 193, 7));
                g2d.fillRect(20, legendY, 12, 12);
                g2d.setColor(KisiselFinans_100.this.RENK_KOYU);
                g2d.drawString("Kira (%70)", 38, legendY + 10);
                g2d.setColor(new Color(23, 162, 184));
                g2d.fillRect(120, legendY, 12, 12);
                g2d.setColor(KisiselFinans_100.this.RENK_KOYU);
                g2d.drawString("Gida (%18)", 138, legendY + 10);
                g2d.setColor(new Color(111, 66, 193));
                g2d.fillRect(220, legendY, 12, 12);
                g2d.setColor(KisiselFinans_100.this.RENK_KOYU);
                g2d.drawString("Fatura (%12)", 238, legendY + 10);
            }
        };
        grafikCizimPaneli.setBackground(this.RENK_PANEL);
        panel.add(grafikCizimPaneli, "Center");
        return panel;
    }

    private JPanel altPanelOlustur() {
        JPanel altPanel = new JPanel(new FlowLayout(1));
        altPanel.setBackground(this.RENK_ARKA);
        JLabel lblFooter = new JLabel("© 2026 Kisisel Finans Asistani | Asama 100: MySQL Veritabani Baglantisi");
        lblFooter.setFont(new Font("Segoe UI", 0, 11));
        lblFooter.setForeground(this.RENK_HAFIF);
        altPanel.add(lblFooter);
        return altPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> (new KisiselFinans_100()).setVisible(true));
    }
}
