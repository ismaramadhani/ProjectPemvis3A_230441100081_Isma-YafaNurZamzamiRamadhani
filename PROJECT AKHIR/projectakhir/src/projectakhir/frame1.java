/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectakhir;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
/**
 *
 * @author HP VICTUS
 */
public class frame1 extends javax.swing.JFrame {
Connection conn = koneksi.getConnection();
int id;
double calories = 0;
int currentTextHealth = 0;
int currentTextMental = 0;
int id_user ;
private String username;

    /**
     * Creates new form frame1
     */
    public frame1(int idd, String u) {
        initComponents();
        this.id_user = idd;
        this.username = u;
        load_dataA(id_user);
        load_dataH(id_user);
        load_dataM(id_user);
        
        load_recom_Activity();
        load_recom_HealthTips();
        load_recom_MentalHealth();
        
        health_recom();
        mental_recom(id_user);
        
        labelWelcome.setText("Selamat datang, " + username + "!");
    }
java.util.Date tglsekarang = new java.util.Date();
SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMMMM yyyy", Locale.getDefault());
String tanggal = smpdtfmt.format(tglsekarang);

private void load_dataA(int id_user) {
    // Membuat model tabel baru
    DefaultTableModel modelA = new DefaultTableModel();
    modelA.addColumn("ID");
    modelA.addColumn("Date");
    modelA.addColumn("Activity");
    modelA.addColumn("Duration");
    modelA.addColumn("Calories");

    try {
        // Query untuk mengambil data berdasarkan id_user
        String sql = "SELECT * FROM activity_tracker WHERE id_user = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id_user);
        ResultSet res = pst.executeQuery();

        // Menambahkan data ke model tabel
        while (res.next()) {
            modelA.addRow(new Object[]{
                res.getString("id"),
                res.getString("Date"),
                res.getString("Activity"),
                res.getString("Duration"),
                res.getString("Calories")
            });
        }

        // Menampilkan model pada tabel
        TableActivity.setModel(modelA);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}


private void load_dataH(int id_user) {
    try {
        // Query untuk mengambil data berdasarkan id_user
        String sql = "SELECT * FROM health_tips WHERE id_user = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id_user); // Set parameter id_user
        ResultSet res = pst.executeQuery();

        currentTextHealth = 0; // Inisialisasi atau reset nilai currentTextHealth

        // Iterasi melalui hasil query
        while (res.next()) {
            String tips = res.getString("tips");
            displayInHealth(tips); 
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}


private void load_dataM(int id_user) {
    try {
        // Query untuk mengambil data berdasarkan id_user
        String sql = "SELECT * FROM mental_health WHERE id_user = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id_user); // Set parameter id_user

        ResultSet res = pst.executeQuery();
        currentTextMental = 0; // Inisialisasi atau reset nilai currentTextMental

        // Iterasi melalui hasil query
        while (res.next()) {
            String journey = res.getString("Journey");
            displayInMental(journey); 
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

private void displayInHealth(String tips) {
    // Tentukan batas panjang teks per baris (misalnya 10 karakter)
    int maxLength = 60;
    
    // Pisahkan teks menjadi beberapa baris jika panjangnya melebihi batas
    StringBuilder formattedTips = new StringBuilder();
    while (tips.length() > maxLength) {
        formattedTips.append(tips, 0, maxLength).append("\n"); 
        tips = tips.substring(maxLength); 
    }
    formattedTips.append(tips); // Tambahkan sisa teks yang lebih pendek dari maxLength
 
    switch (currentTextHealth) {
        case 0:
            txtSatu.setText(" - " + formattedTips.toString());
            break;
        case 1:
            txtDua.setText(" - " + formattedTips.toString());
            break;
        case 2:
            txtTiga.setText(" - " + formattedTips.toString());
            break;
    }

    
    currentTextHealth = (currentTextHealth + 1) % 3; 
}

private void displayInMental(String Journey) {
    // Tentukan batas panjang teks per baris (misalnya 70 karakter)
    int maxLength = 60;

    // Pisahkan teks menjadi beberapa baris jika panjangnya melebihi batas
    StringBuilder formattedJourney = new StringBuilder();
    while (Journey.length() > maxLength) {
        formattedJourney.append(Journey, 0, maxLength).append("\n"); 
        formattedJourney.append(Journey, 0, maxLength).append("\n"); 
        Journey = Journey.substring(maxLength); 
    }
    formattedJourney.append(Journey); 

    switch (currentTextMental) {
        case 0:
            txtSatuM.setText(" - " + formattedJourney.toString());
            break;
        case 1:
            txtDuaM.setText(" - " + formattedJourney.toString());
            break;
        case 2:
            txtTigaM.setText(" - " + formattedJourney.toString());
            break;
        case 3:
            txtEmpatM.setText(" - " + formattedJourney.toString());
            break;
    }

    currentTextMental = (currentTextMental + 1) % 4;
}

private double data(String A, int d) throws Exception {
    switch (A) {
        case "Running":
            return calories = d * 10; 
        case "Swimming":
            return calories = d * 8; 
        case "Jogging":
            return calories = d * 7; 
        case "Basket":
            return calories = d * 9;
        case "Bicyle":
            return calories = d * 6; 
        case "FootBall":
            return calories = d * 11; 
        case "Badminton":
            return calories = d * 8.5; 
        case "Fitness":
            return calories = d * 7.5; 
        case "Yoga":
            return calories = d * 3; 
        case "Skipping":
            return calories = d * 12; 
        default:
            throw new Exception("Aktivitas tidak dikenali");
           
    }
}

private void update_data(String oldTips) {
    try {
        // Meminta input dari pengguna untuk tips baru
        String newTips = JOptionPane.showInputDialog(null, "Masukkan health tips baru:", oldTips);

        if (newTips != null && !newTips.isEmpty()) {
            // Query untuk mendapatkan ID berdasarkan tips lama
            String sqll = "SELECT id FROM health_tips WHERE tips = ?";
            PreparedStatement pss = conn.prepareStatement(sqll);
            pss.setString(1, oldTips);
            ResultSet res = pss.executeQuery();

            if (res.next()) {
                // Ambil ID dari hasil query
                int id = res.getInt("id");
                
                // Query untuk memperbarui data berdasarkan ID
                String sql = "UPDATE health_tips SET tips = ? WHERE id = ? AND id_user = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, newTips);
                ps.setInt(2, id);
                ps.setInt(3, id_user);
                
                // Eksekusi update dan cek apakah data berhasil diubah
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
                    load_dataH(id_user); // Muat ulang data (sesuaikan dengan metode Anda)
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau tidak diubah.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tips lama tidak ditemukan.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Teks baru tidak valid.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

private void delete_data(String oldTips) {
    try {
        // Konfirmasi penghapusan
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Apakah Anda yakin ingin menghapus tips health ini?\n" + oldTips,
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        // Jika pengguna memilih "Yes"
        if (confirm == JOptionPane.YES_OPTION) {
            // Query SQL untuk menghapus data
            String sql = "DELETE FROM health_tips WHERE tips = ? AND id_user =?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set parameter query
            ps.setString(1, oldTips);
            ps.setInt(2, id_user);

            // Eksekusi query
            int rowsDeleted = ps.executeUpdate();

            // Beri notifikasi hasil
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                load_dataH(id_user); // Memuat ulang data di text area
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

private void update_dataM (String oldTipsM){
    try {
        String newTips = JOptionPane.showInputDialog(null, "Masukkan Journey baru:", oldTipsM);
            if (newTips != null && !newTips.isEmpty()) {
                String sql = "UPDATE mental_health SET Journey = ? WHERE Journey = ? AND id_user = ?"; // Update berdasarkan teks lama
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, newTips); 
                ps.setString(2, oldTipsM); 
                ps.setInt(3, id_user); 

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
                    load_dataM(id_user); 
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau tidak diubah.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Teks baru tidak valid.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
}

private void delete_dataM(String oldTipsM) {
    try {
        // Konfirmasi penghapusan
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Apakah Anda yakin ingin menghapus Journey ini?\n" + oldTipsM,
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        // Jika pengguna memilih "Yes"
        if (confirm == JOptionPane.YES_OPTION) {
            // Query SQL untuk menghapus data
            String sql = "DELETE FROM mental_health WHERE Journey = ? AND id_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set parameter query
            ps.setString(1, oldTipsM);
            ps.setInt(2, id_user);
            // Eksekusi query
            int rowsDeleted = ps.executeUpdate();

            // Beri notifikasi hasil
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                load_dataM(id_user); // Memuat ulang data di text area
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

private void health_recom () {
    String[] tips = {
            "Jangan lupa untuk beristirahat setiap beberapa jam.",
            "Cobalah untuk minum lebih banyak air setiap hari.",
            "Berjalan kaki setiap pagi bisa meningkatkan kesehatan.",
            "Jaga pola makan sehat dengan banyak sayuran dan buah.",
            "Tidur yang cukup sangat penting untuk kesehatan mental.",
            "Latihan ringan seperti yoga bisa membantu meredakan stres.",
            "Cobalah meditasi untuk meningkatkan fokus dan ketenangan.",
            "Selalu luangkan waktu untuk berolahraga secara rutin.",
            "Jangan terlalu banyak duduk, coba berdiri dan bergerak setiap 30 menit."
        };
        ArrayList<String> shuffledTips = new ArrayList<>();
        Collections.addAll(shuffledTips, tips);
        Collections.shuffle(shuffledTips);

        // Menampilkan tiga pesan acak di JTextField
        txtatips1.setText(" - " + shuffledTips.get(0));
        txtatips2.setText(" - " + shuffledTips.get(1));
        

}
private void mental_recom(int id_user) {
    try {
        // Query untuk mengambil 3 data paling lama berdasarkan kolom waktu
        String sql = "SELECT Date, Journey FROM mental_health WHERE id_user = ? ORDER BY Date ASC LIMIT 3";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id_user); // Set parameter id_user

        ResultSet res = pst.executeQuery();

        // Inisialisasi array untuk menyimpan data
        String[] data = {"", "", ""}; // Inisialisasi kosong untuk memastikan JTextField dibersihkan
        int i = 0;

        // Iterasi melalui hasil query
        while (res.next() && i < 3) {
            String date = res.getString("Date");
            String journey = res.getString("Journey");
            data[i] = date + ": " + journey; 
            i++;
        }

        // Tampilkan data ke JTextField sesuai urutan
        txtSatuR.setText(data[0]); 
        txtDuaR.setText(data[1]);  
        txtTigaR.setText(data[2]);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}



private void load_recom_Activity() {
    try {
        // Query SQL dengan parameter id_user
        String sql = "SELECT Date, Activity, Duration, Calories FROM activity_tracker WHERE id_user = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id_user);  // Set id_user sebagai parameter pada query

        // Hasilkan StringBuilder untuk menyimpan data
        StringBuilder activityData = new StringBuilder();
        activityData.setLength(0); // Reset data sebelumnya

        // Eksekusi query dan iterasi melalui hasil
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            String date = res.getString("Date");
            String activity = res.getString("Activity");
            String duration = res.getString("Duration");
            String calories = res.getString("Calories");

            // Format setiap baris data
            activityData.append("Date: ").append(date)
                        .append(", Activity: ").append(activity)
                        .append(", Duration: ").append(duration + " Menit ")
                        .append(", Calories: ").append(calories)
                        .append("\n");
        }

        // Tampilkan hasil ke jTextArea
        jTextArea1.setText(activityData.toString());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

private void load_recom_HealthTips() {
    try {
        // Query SQL untuk mengambil health tips berdasarkan id_user
        String sql = "SELECT tips FROM health_tips ";
        PreparedStatement pst = conn.prepareStatement(sql);

        // Hasilkan StringBuilder untuk menyimpan data
        StringBuilder healthTipsData = new StringBuilder();
        healthTipsData.setLength(0); // Reset data sebelumnya

        // Eksekusi query dan iterasi melalui hasil
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            String tips = res.getString("tips");

            // Format setiap baris data
            healthTipsData.append(" - ").append(tips).append("\n"); // Menambahkan spasi antara tips
        }

        // Tampilkan hasil ke jTextArea
        jTextArea2.setText(healthTipsData.toString());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}

private void load_recom_MentalHealth() {
    try {
        // Query SQL untuk mengambil health tips berdasarkan id_user
        String sql = "SELECT Journey FROM mental_health";
        PreparedStatement pst = conn.prepareStatement(sql);

        // Hasilkan StringBuilder untuk menyimpan data
        StringBuilder mentalHealthData = new StringBuilder();
        mentalHealthData.setLength(0); // Reset data sebelumnya

        // Eksekusi query dan iterasi melalui hasil
        ResultSet res = pst.executeQuery();
        while (res.next()) {
            String journey = res.getString("Journey");

            // Format setiap baris data
            mentalHealthData.append(" - ").append(journey).append("\n"); // Menambahkan spasi antara tips
        }

        // Tampilkan hasil ke jTextArea
        jTextArea3.setText(mentalHealthData.toString());

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}



private void kosongA (){
    cbActivity.setSelectedIndex(0);
    txtDuration.setText("");
}

private void kosongH (){
    txtTips.setText("");
}

private void kosongM (){
    txtJourney.setText("");
}
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        labelWelcome = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableActivity = new javax.swing.JTable();
        cbActivity = new javax.swing.JComboBox<>();
        txtDuration = new javax.swing.JTextField();
        btnAddA = new javax.swing.JButton();
        btnUpdateA = new javax.swing.JButton();
        btnDeleteA = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtTips = new javax.swing.JTextArea();
        btnAddH = new javax.swing.JButton();
        txtatips1 = new javax.swing.JTextField();
        txtatips2 = new javax.swing.JTextField();
        txtDua = new javax.swing.JTextArea();
        txtTiga = new javax.swing.JTextArea();
        txtSatu = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtJourney = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnAddM = new javax.swing.JButton();
        txtSatuM = new javax.swing.JTextArea();
        txtDuaM = new javax.swing.JTextArea();
        txtTigaM = new javax.swing.JTextArea();
        txtEmpatM = new javax.swing.JTextArea();
        txtSatuR = new javax.swing.JTextField();
        txtDuaR = new javax.swing.JTextField();
        txtTigaR = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectakhir/HEADER.png"))); // NOI18N
        jLabel16.setText("jLabel16");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.setBackground(new java.awt.Color(153, 204, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelWelcome.setFont(new java.awt.Font("Berlin Sans FB Demi", 3, 36)); // NOI18N
        labelWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(labelWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 717, 154));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/projectakhir/home (1).png"))); // NOI18N
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 670));

        jTabbedPane1.addTab("Home", jPanel3);

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("RECOMENDED");
        jLabel2.setToolTipText("");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 767, 55));

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 18)); // NOI18N
        jLabel3.setText("Activity Tracker :");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 144, 38));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 650, 112));

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        jLabel4.setText("Health Tips :");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 229, 107, -1));

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 262, 650, 112));

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 18)); // NOI18N
        jLabel5.setText("Mental Health Journal :");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 386, 213, -1));

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 419, 650, 112));

        jTabbedPane1.addTab("Dashboard", jPanel4);

        jTabbedPane2.setBackground(new java.awt.Color(153, 204, 255));
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel6.setText("Activity Tracker ");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 6, -1, -1));

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel8.setText("Activity :");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 55, 91, -1));

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel9.setText("Duration : ");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 101, 103, -1));

        TableActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableActivityMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableActivity);

        jPanel7.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 162, -1, -1));

        cbActivity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilihan Activity ", "Running", "Swimming", "Jogging", "Basket", "Bicyle", "FootBall", "Badminton", "Fitness", "Yoga", "Skipping" }));
        jPanel7.add(cbActivity, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 52, 365, 35));

        txtDuration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDurationActionPerformed(evt);
            }
        });
        jPanel7.add(txtDuration, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 99, 365, 33));

        btnAddA.setText("Add Activity");
        btnAddA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAActionPerformed(evt);
            }
        });
        jPanel7.add(btnAddA, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 610, -1, -1));

        btnUpdateA.setText("Update Activity");
        btnUpdateA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAActionPerformed(evt);
            }
        });
        jPanel7.add(btnUpdateA, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 610, -1, -1));

        btnDeleteA.setText("Delete Activity");
        btnDeleteA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAActionPerformed(evt);
            }
        });
        jPanel7.add(btnDeleteA, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 610, -1, -1));

        jTabbedPane2.addTab("Activity Tracker", jPanel7);

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel10.setText("Health Tips");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 6, 141, -1));

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel11.setText("Add Tips");
        jPanel8.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 71, 90, -1));

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel12.setText("Today");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 143, 90, -1));

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel13.setText("Additional Tips");
        jPanel8.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 230, -1));

        txtTips.setColumns(20);
        txtTips.setRows(5);
        jScrollPane5.setViewportView(txtTips);

        jPanel8.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 52, 430, 68));

        btnAddH.setText("Add Tips");
        btnAddH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHActionPerformed(evt);
            }
        });
        jPanel8.add(btnAddH, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 530, 90, 30));

        txtatips1.setEditable(false);
        txtatips1.setBackground(new java.awt.Color(204, 204, 255));
        txtatips1.setFont(new java.awt.Font("Century751 No2 BT", 0, 14)); // NOI18N
        txtatips1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel8.add(txtatips1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 551, 30));

        txtatips2.setEditable(false);
        txtatips2.setBackground(new java.awt.Color(204, 204, 255));
        txtatips2.setFont(new java.awt.Font("Century751 No2 BT", 0, 14)); // NOI18N
        txtatips2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        txtatips2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtatips2ActionPerformed(evt);
            }
        });
        jPanel8.add(txtatips2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 551, 30));

        txtDua.setEditable(false);
        txtDua.setBackground(new java.awt.Color(204, 204, 255));
        txtDua.setColumns(10);
        txtDua.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtDua.setRows(5);
        txtDua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtDua.setOpaque(false);
        txtDua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDuaMouseClicked(evt);
            }
        });
        jPanel8.add(txtDua, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 560, 60));

        txtTiga.setEditable(false);
        txtTiga.setBackground(new java.awt.Color(204, 204, 255));
        txtTiga.setColumns(20);
        txtTiga.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtTiga.setRows(5);
        txtTiga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtTiga.setOpaque(false);
        txtTiga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTigaMouseClicked(evt);
            }
        });
        jPanel8.add(txtTiga, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 560, 60));

        txtSatu.setEditable(false);
        txtSatu.setBackground(new java.awt.Color(204, 204, 255));
        txtSatu.setColumns(20);
        txtSatu.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtSatu.setRows(5);
        txtSatu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSatu.setOpaque(false);
        txtSatu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSatuMouseClicked(evt);
            }
        });
        jPanel8.add(txtSatu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 560, 60));

        jTabbedPane2.addTab("Health Tips", jPanel8);

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        jLabel7.setText("Mental Health Journey");
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 259, -1));

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel14.setText("Add Journey");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        txtJourney.setColumns(20);
        txtJourney.setRows(5);
        jScrollPane6.setViewportView(txtJourney);

        jPanel9.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 404, -1));

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel15.setText("Today");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 191, -1, -1));

        jLabel19.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 20)); // NOI18N
        jLabel19.setText("Previous Entries ");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        btnAddM.setText("Add Journey");
        btnAddM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMActionPerformed(evt);
            }
        });
        jPanel9.add(btnAddM, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 640, 110, 30));

        txtSatuM.setEditable(false);
        txtSatuM.setBackground(new java.awt.Color(204, 204, 255));
        txtSatuM.setColumns(20);
        txtSatuM.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtSatuM.setRows(5);
        txtSatuM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtSatuM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSatuMMouseClicked(evt);
            }
        });
        jPanel9.add(txtSatuM, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 570, 50));

        txtDuaM.setEditable(false);
        txtDuaM.setBackground(new java.awt.Color(204, 204, 255));
        txtDuaM.setColumns(20);
        txtDuaM.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtDuaM.setRows(5);
        txtDuaM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtDuaM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDuaMMouseClicked(evt);
            }
        });
        jPanel9.add(txtDuaM, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 570, 50));

        txtTigaM.setEditable(false);
        txtTigaM.setBackground(new java.awt.Color(204, 204, 255));
        txtTigaM.setColumns(20);
        txtTigaM.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtTigaM.setRows(5);
        txtTigaM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtTigaM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTigaMMouseClicked(evt);
            }
        });
        jPanel9.add(txtTigaM, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 570, 50));

        txtEmpatM.setEditable(false);
        txtEmpatM.setBackground(new java.awt.Color(204, 204, 255));
        txtEmpatM.setColumns(20);
        txtEmpatM.setFont(new java.awt.Font("Century751 No2 BT", 0, 18)); // NOI18N
        txtEmpatM.setRows(5);
        txtEmpatM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtEmpatM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmpatMMouseClicked(evt);
            }
        });
        jPanel9.add(txtEmpatM, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 570, 50));

        txtSatuR.setBackground(new java.awt.Color(204, 204, 255));
        txtSatuR.setFont(new java.awt.Font("Century751 No2 BT", 0, 14)); // NOI18N
        txtSatuR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel9.add(txtSatuR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 570, 30));

        txtDuaR.setBackground(new java.awt.Color(204, 204, 255));
        txtDuaR.setFont(new java.awt.Font("Century751 No2 BT", 0, 14)); // NOI18N
        txtDuaR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel9.add(txtDuaR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 570, 30));

        txtTigaR.setBackground(new java.awt.Color(204, 204, 255));
        txtTigaR.setFont(new java.awt.Font("Century751 No2 BT", 0, 14)); // NOI18N
        txtTigaR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel9.add(txtTigaR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, 570, 30));

        jTabbedPane2.addTab("Mental Health", jPanel9);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        jTabbedPane1.addTab("Task", jPanel5);

        jPanel1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jScrollPane7.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDurationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDurationActionPerformed

    private void btnAddAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAActionPerformed
        // TODO add your handling code here:
    try {
        if (cbActivity.getSelectedIndex() == 0 || txtDuration.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                cbActivity.getSelectedIndex() == 0 ? 
                "Silakan pilih aktivitas terlebih dahulu!" : 
                "Durasi tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int durasi = Integer.parseInt(txtDuration.getText());
        String Activity = cbActivity.getSelectedItem().toString();
        data(Activity, durasi);
        
        String sql = "INSERT INTO activity_tracker(Date, Activity, Duration, Calories,id_user)" + "VALUES (?, ?, ?, ?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tanggal);
        ps.setString(2, Activity);
        ps.setInt(3, durasi);
        ps.setDouble(4, calories);
        ps.setInt(5, id_user);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        
        load_dataA(id_user);
        kosongA();
        
    }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnAddAActionPerformed

    private void TableActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableActivityMouseClicked
        // TODO add your handling code here:
        int row = TableActivity.getSelectedRow(); 
        
        if (row != -1) {
            id = Integer.parseInt(TableActivity.getValueAt(row, 0).toString());
            txtDuration.setText(TableActivity.getValueAt(row, 3).toString()); 
            String activity = TableActivity.getValueAt(row, 2).toString();
            for (int i = 0; i < cbActivity.getItemCount(); i++) {
            if (cbActivity.getItemAt(i).equals(activity)) {
            cbActivity.setSelectedIndex(i);
            break;
                }
            }
        }
    }//GEN-LAST:event_TableActivityMouseClicked

    private void btnUpdateAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAActionPerformed
        // TODO add your handling code here:
        try{
            if (cbActivity.getSelectedIndex() == 0 || txtDuration.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                cbActivity.getSelectedIndex() == 0 ? 
                "Silakan pilih aktivitas terlebih dahulu!" : 
                "Durasi tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menyimpan perubahan data ini?", "Konfirmasi Perubahan", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int durasi = Integer.parseInt(txtDuration.getText());
                    String Activity = cbActivity.getSelectedItem().toString();
                    data(Activity, durasi);
                    String sql = "UPDATE activity_tracker SET Duration = ?, Activity = ?, Calories = ? WHERE id = ? AND id_user = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, durasi);
                    ps.setString(2, Activity);
                    ps.setDouble(3, calories);
                    ps.setInt(4, id);
                    ps.setInt(5, id_user);
                    ps.executeUpdate();
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");
                        load_dataA(id_user); 
                        kosongA(); 
                    }else {
                        JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau tidak dapat diupdate!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    }
                } 
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
            
            
    }//GEN-LAST:event_btnUpdateAActionPerformed

    private void btnDeleteAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAActionPerformed
        // TODO add your handling code here:
        try{
            if (cbActivity.getSelectedIndex() == 0 || txtDuration.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                cbActivity.getSelectedIndex() == 0 ? 
                "Silakan pilih aktivitas terlebih dahulu!" : 
                "Durasi tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String sql = "DELETE FROM activity_tracker WHERE id = ? AND id_user = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, id);
                    ps.setInt(2, id_user);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    load_dataA(id_user); 
                    kosongA(); 
                    
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnDeleteAActionPerformed

    private void btnAddHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHActionPerformed
        // TODO add your handling code here:
        try{
            if (txtTips.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Tips tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
            }
            String tips = txtTips.getText();
            String sql = "INSERT INTO health_tips(tips,id_user)" + "VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tips);
            ps.setInt(2, id_user);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            
            load_dataH(id_user);
            kosongH();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnAddHActionPerformed

    private void btnAddMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMActionPerformed
        // TODO add your handling code here:
        try{
            String journey = txtJourney.getText();
            String sql = "INSERT INTO mental_health (date, Journey, id_user)" + "VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tanggal);
            ps.setString(2, journey);
            ps.setInt(3, id_user);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            
            load_dataM(id_user);
            kosongM();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnAddMActionPerformed

    private void txtatips2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtatips2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtatips2ActionPerformed

    private void txtSatuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSatuMouseClicked
        // TODO add your handling code here:
        String oldTips = txtSatu.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_data(oldTips);
            } else if (choice == 1) {
                delete_data(oldTips);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtSatuMouseClicked

    private void txtDuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDuaMouseClicked
        // TODO add your handling code here:
        String oldTips = txtDua.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_data(oldTips);
            } else if (choice == 1) {
                delete_data(oldTips);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtDuaMouseClicked

    private void txtTigaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTigaMouseClicked
        // TODO add your handling code here:
        String oldTips = txtTiga.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_data(oldTips);
            } else if (choice == 1) {
                delete_data(oldTips);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtTigaMouseClicked

    private void txtSatuMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSatuMMouseClicked
        // TODO add your handling code here:
        String oldTipsM = txtSatuM.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_dataM(oldTipsM);
            } else if (choice == 1) {
                delete_dataM(oldTipsM);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtSatuMMouseClicked

    private void txtDuaMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDuaMMouseClicked
        // TODO add your handling code here:
        String oldTipsM = txtDuaM.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_dataM(oldTipsM);
            } else if (choice == 1) {
                delete_dataM(oldTipsM);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtDuaMMouseClicked

    private void txtTigaMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTigaMMouseClicked
        // TODO add your handling code here:
        String oldTipsM = txtTigaM.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_dataM(oldTipsM);
            } else if (choice == 1) {
                delete_dataM(oldTipsM);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtTigaMMouseClicked

    private void txtEmpatMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmpatMMouseClicked
        // TODO add your handling code here:
        String oldTipsM = txtEmpatM.getText().replace(" - ", "");
        try {
            String[] options = {"Update", "Hapus"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "Apa yang ingin Anda lakukan pada data ini?",
                "Pilih Aksi",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            if (choice == 0) {
                update_dataM(oldTipsM);
            } else if (choice == 1) {
                delete_dataM(oldTipsM);
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada aksi yang dipilih.");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txtEmpatMMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frame1(0,"").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableActivity;
    private javax.swing.JButton btnAddA;
    private javax.swing.JButton btnAddH;
    private javax.swing.JButton btnAddM;
    private javax.swing.JButton btnDeleteA;
    private javax.swing.JButton btnUpdateA;
    private javax.swing.JComboBox<String> cbActivity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel labelWelcome;
    private javax.swing.JTextArea txtDua;
    private javax.swing.JTextArea txtDuaM;
    private javax.swing.JTextField txtDuaR;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextArea txtEmpatM;
    private javax.swing.JTextArea txtJourney;
    private javax.swing.JTextArea txtSatu;
    private javax.swing.JTextArea txtSatuM;
    private javax.swing.JTextField txtSatuR;
    private javax.swing.JTextArea txtTiga;
    private javax.swing.JTextArea txtTigaM;
    private javax.swing.JTextField txtTigaR;
    private javax.swing.JTextArea txtTips;
    private javax.swing.JTextField txtatips1;
    private javax.swing.JTextField txtatips2;
    // End of variables declaration//GEN-END:variables
}
