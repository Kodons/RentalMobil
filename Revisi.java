
import java.util.*;

class Mobil {

    private String idMobil;
    private String nama;
    private String status;
    private double hargaSewa;

    public Mobil(String idMobil, String nama, String status, double hargaSewa) {
        this.idMobil = idMobil;
        this.nama = nama;
        this.status = status;
        this.hargaSewa = hargaSewa;
    }

    // Getter dan Setter
    public String getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(String idMobil) {
        this.idMobil = idMobil;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(double hargaSewa) {
        this.hargaSewa = hargaSewa;
    }
}

class Customer {

    private String idCustomer;
    private String username;
    private String password;
    private NodeMobil mobil;

    public Customer(String idCustomer, String username, String password) {
        this.idCustomer = idCustomer;
        this.username = username;
        this.password = password;
    }

    // Getter dan Setter
    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NodeMobil getMobil() {
        return mobil;
    }

    public void setMobil(NodeMobil mobil) {
        this.mobil = mobil;
    }
}

class Transaksi {

    private String idTransaksi;
    private String idCustomer;
    private String idMobil;
    private double totalBayar;
    private String tanggal;

    public Transaksi(String idTransaksi, String idCustomer, String idMobil, double totalBayar, String tanggal) {
        this.idTransaksi = idTransaksi;
        this.idCustomer = idCustomer;
        this.idMobil = idMobil;
        this.totalBayar = totalBayar;
        this.tanggal = tanggal;
    }

    // Getter dan Setter
    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(String idMobil) {
        this.idMobil = idMobil;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}

class NodeMobil {

    Mobil mobil;
    NodeMobil left;
    NodeMobil right;

    public NodeMobil(Mobil mobil) {
        this.mobil = mobil;
        this.left = null;
        this.right = null;
    }
}

class NodeCustomer {

    private Customer customer;
    private NodeCustomer next;

    public NodeCustomer(Customer customer) {
        this.customer = customer;
        this.next = null;
    }

    // Getter dan Setter
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public NodeCustomer getNext() {
        return next;
    }

    public void setNext(NodeCustomer next) {
        this.next = next;
    }

}

class NodeTransaksi {

    private Transaksi transaksi;
    private NodeTransaksi next;
    private NodeCustomer customer;
    private NodeMobil mobil;

    public NodeTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
        this.next = null;
        this.customer = null;
        this.mobil = null;
    }

    // Getter dan Setter
    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public NodeTransaksi getNext() {
        return next;
    }

    public void setNext(NodeTransaksi next) {
        this.next = next;
    }

    public NodeCustomer getCusNodeCustomer() {
        return customer;
    }

    public NodeMobil getMobil() {
        return mobil;
    }

    public void setCustomer(NodeCustomer customer) {
        this.customer = customer;
    }

    public void setMobil(NodeMobil mobil) {
        this.mobil = mobil;
    }

}

class TreeMobil {

    NodeMobil root;

    // Tambahkan mobil baru ke dalam tree
    public void tambahMobil(Mobil mobil) {
        if (mobil == null) {
            System.out.println("Mobil tidak valid!");
            return;
        }
        root = tambahMobilRecursive(root, mobil);
    }

    private NodeMobil tambahMobilRecursive(NodeMobil node, Mobil mobil) {
        if (node == null) {
            return new NodeMobil(mobil);
        }

        // Bandingkan ID mobil untuk menentukan posisi di tree
        if (mobil.getIdMobil().compareTo(node.mobil.getIdMobil()) < 0) {
            node.left = tambahMobilRecursive(node.left, mobil);
        } else if (mobil.getIdMobil().compareTo(node.mobil.getIdMobil()) > 0) {
            node.right = tambahMobilRecursive(node.right, mobil);
        } else {
            System.out.println("Gagal menambahkan mobil! ID mobil sudah ada.");
        }

        return node;
    }

    // Cek apakah ID mobil sudah ada di tree
    public boolean cekIdMobil(String idMobil) {
        return cekIdMobilRecursive(root, idMobil);
    }

    private boolean cekIdMobilRecursive(NodeMobil node, String idMobil) {
        if (node == null) {
            return false;
        }

        if (idMobil.equals(node.mobil.getIdMobil())) {
            return true;
        }

        if (idMobil.compareTo(node.mobil.getIdMobil()) < 0) {
            return cekIdMobilRecursive(node.left, idMobil);
        } else {
            return cekIdMobilRecursive(node.right, idMobil);
        }
    }

    // Tampilkan semua mobil dalam tree
    public void tampilkanDaftarMobil() {
        tampilkanDaftarMobilRecursive(root);
    }

    private void tampilkanDaftarMobilRecursive(NodeMobil node) {
        if (node != null) {
            tampilkanDaftarMobilRecursive(node.left);
            System.out.println("ID Mobil: " + node.mobil.getIdMobil());
            System.out.println("Nama Mobil: " + node.mobil.getNama());
            System.out.println("Status: " + node.mobil.getStatus());
            System.out.println("Harga Sewa: " + node.mobil.getHargaSewa());
            System.out.println("--------------------");
            tampilkanDaftarMobilRecursive(node.right);
        }
    }

    // Hapus mobil berdasarkan ID
    public void hapusMobil(String idMobil) {
        if (idMobil == null || idMobil.isEmpty()) {
            System.out.println("ID Mobil tidak valid!");
            return;
        }
        root = hapusMobilRecursive(root, idMobil);
    }

    private NodeMobil hapusMobilRecursive(NodeMobil node, String idMobil) {
        if (node == null) {
            return null;
        }

        if (idMobil.compareTo(node.mobil.getIdMobil()) < 0) {
            node.left = hapusMobilRecursive(node.left, idMobil);
        } else if (idMobil.compareTo(node.mobil.getIdMobil()) > 0) {
            node.right = hapusMobilRecursive(node.right, idMobil);
        } else {
            // Node yang ingin dihapus ditemukan
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                NodeMobil temp = cariNodeTerkecil(node.right);
                node.mobil = temp.mobil;
                node.right = hapusMobilRecursive(node.right, temp.mobil.getIdMobil());
            }
        }

        return node;
    }

    private NodeMobil cariNodeTerkecil(NodeMobil node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Mobil cariMobil(String idMobil) {
        return cariMobilRecursive(root, idMobil);
    }

    private Mobil cariMobilRecursive(NodeMobil node, String idMobil) {
        if (node == null) {
            return null;
        }
        if (idMobil.equals(node.mobil.getIdMobil())) {
            return node.mobil;
        }
        if (idMobil.compareTo(node.mobil.getIdMobil()) < 0) {
            return cariMobilRecursive(node.left, idMobil);
        } else {
            return cariMobilRecursive(node.right, idMobil);
        }
    }

}

public class Revisi {

    static TreeMobil daftarMobil = new TreeMobil();
    static LinkedList<Customer> customers = new LinkedList<>();
    static LinkedList<Transaksi> transaksi = new LinkedList<>();
    static Scanner input = new Scanner(System.in);
    static Customer customerLogin = null;

    public static void main(String[] args) {

        int pilih;

        do {
            clearScreen();
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("3. Exit");

            System.out.print("\nInput : ");
            pilih = input.nextInt();

            switch (pilih) {
                case 1 ->
                    login();
                case 2 ->
                    registrasi();
                case 3 -> {
                    System.out.println("Terima Kasih !");
                }
                default ->
                    System.out.println("\nInputan tidak valid !");
            }
        } while (pilih != 3);
    }

    static void login() {
        System.out.print("ID : ");
        String id = input.next();
        System.out.print("Username : ");
        String username = input.next();
        System.out.print("Password : ");
        String password = input.next();
        boolean validate = false;
        for (Customer customer : customers) {
            if (customer.getIdCustomer().equals(id) && customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                validate = true;
                customerLogin = customer; // Simpan customer yang login
                break;
            }
        }
        if (id.equals("0") && username.equals("admin") && password.equals("admin")) {
            menuAdmin();
        } else if (validate) {
            menuCustomer();
        } else {
            System.out.println("Login gagal! Periksa ID, username, dan password.");
            next();
        }
    }

    static void registrasi() {

        System.out.print("Masukan ID : ");
        String id = input.next();
        System.out.print("Masukan username : ");
        String username = input.next();
        System.out.print("Masukan password : ");
        String password = input.next();

        for (Customer customer : customers) {
            if (customer.getIdCustomer().equals(id)) {
                System.out.println("ID customer sudah digunakan!");
                return;
            }
        }

        if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
            System.out.println("\nUsername dan Password tidak dapat digunakan !");
        } else {
            Customer newCustomer = new Customer(id, username, password);
            customers.add(newCustomer);
            System.out.println("\nRegistrasi berhasil !");
        }
        next();
    }

    static void menuAdmin() {
        clearScreen();
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Kelola Data Kendaraan");
            System.out.println("2. Kelola Data Pelanggan");
            System.out.println("3. Laporan Transaksi dan Pendapatan");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");

            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1 ->
                    manageVehicles();
                case 2 ->
                    manageCustomers();
                case 3 ->
                    generateReport();
                case 4 -> {
                    return;
                }
                default ->
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void manageVehicles() {
        clearScreen();
        while (true) {
            System.out.println("\n=== Kelola Kendaraan ===");
            System.out.println("1. Tampilkan Semua Kendaraan");
            System.out.println("2. Tambah Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu: ");

            int pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1 ->
                    displayVehicles();
                case 2 ->
                    addVehicle();
                case 3 ->
                    deleteVehicle();
                case 4 -> {
                    return;
                }
                default ->
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void addVehicle() {
        System.out.print("ID Mobil: ");
        String idMobil = input.next();
        System.out.print("Nama Mobil: ");
        String nama = input.next();
        System.out.print("Harga Sewa Mobil: ");
        double hargaSewa = input.nextDouble();
        input.nextLine();

        if (daftarMobil.cekIdMobil(idMobil)) {
            System.out.println("Gagal menambahkan mobil! ID mobil sudah ada.");
        } else {
            Mobil mobilBaru = new Mobil(idMobil, nama, "Tersedia", hargaSewa);
            daftarMobil.tambahMobil(mobilBaru);
            System.out.println("Mobil berhasil ditambahkan!");
        }

    }

    static void displayVehicles() {
        daftarMobil.tampilkanDaftarMobil();
    }

    static void deleteVehicle() {
        System.out.print("Masukkan ID Mobil yang ingin dihapus: ");
        String idMobil = input.next();

        if (daftarMobil.cekIdMobil(idMobil)) {
            daftarMobil.hapusMobil(idMobil);
            System.out.println("Mobil berhasil dihapus!");
        } else {
            System.out.println("Mobil tidak ditemukan!");
        }
    }

    static void manageCustomers() {
        if (customers.isEmpty()) {
            System.out.println("Belum ada customer terdaftar.");
        } else {
            System.out.println("Daftar Customer Terdaftar:");
            for (Customer customer : customers) {
                System.out.println("ID Customer: " + customer.getIdCustomer());
                System.out.println("Username: " + customer.getUsername());
                System.out.println("Password: " + customer.getPassword());
                System.out.println("--------------------");
            }
        }
    }

    static void cariCustomer() {
        System.out.print("Masukkan ID Customer: ");
        String idCustomer = input.next();
        boolean ditemukan = false;
        for (Customer customer : customers) {
            if (customer.getIdCustomer().equals(idCustomer)) {
                System.out.println("Customer Ditemukan:");
                System.out.println("ID Customer: " + customer.getIdCustomer());
                System.out.println("Username: " + customer.getUsername());
                System.out.println("Password: " + customer.getPassword());
                ditemukan = true;
                break;
            }
        }
        if (!ditemukan) {
            System.out.println("Customer tidak ditemukan.");
        }
    }

    static void generateReport() {

    }

    static void menuCustomer() {
        clearScreen();
        while (true) {
            System.out.println("Menu Customer");
            System.out.println("1. Sewa Mobil");
            System.out.println("2. Lihat Daftar Mobil Tersedia");
            System.out.println("3. Riwayat Sewa");
            System.out.println("4. Logout");

            int pilih = input.nextInt();
            switch (pilih) {
                case 1:
                    sewaMobil();
                    break;
                case 2:
                    lihatDaftarMobil();
                    break;
                case 3:
                    riwayatSewa();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }

    }

    static void sewaMobil() {
        System.out.println("Daftar Mobil Tersedia:");
        daftarMobil.tampilkanDaftarMobil();

        System.out.print("Masukkan ID Mobil: ");
        String idMobil = input.next();

        Mobil mobil = daftarMobil.cariMobil(idMobil);

        if (mobil != null) {
            System.out.print("Masukkan Tanggal Sewa: ");
            String tanggalSewa = input.next();
            System.out.print("Masukkan Jumlah Hari: ");
            int jumlahHari = input.nextInt();

            double totalBiaya = mobil.getHargaSewa() * jumlahHari;

            System.out.println("Total Biaya: " + totalBiaya);

            System.out.print("Konfirmasi penyewaan (y/n): ");
            String konfirmasi = input.next();

            if (konfirmasi.equalsIgnoreCase("y")) {
                Transaksi transaksiBaru = new Transaksi(
                        "TRX-" + System.currentTimeMillis(),
                        customerLogin.getIdCustomer(),
                        mobil.getIdMobil(),
                        totalBiaya,
                        tanggalSewa
                );
                transaksi.add(transaksiBaru);
                System.out.println("Penyewaan berhasil!");
            } else {
                System.out.println("Penyewaan dibatalkan.");
            }
        } else {
            System.out.println("Mobil tidak tersedia.");
        }
    }

    static void lihatDaftarMobil() {
        daftarMobil.tampilkanDaftarMobil();
    }

    static void riwayatSewa() {
        if (transaksi.isEmpty()) {
            System.out.println("Belum ada riwayat sewa.");
        } else {
            System.out.println("Riwayat Sewa:");
            for (Transaksi transaksi : transaksi) {
                if (transaksi.getIdCustomer().equals(customerLogin.getIdCustomer())) {
                    System.out.println("ID Transaksi: " + transaksi.getIdTransaksi());
                    System.out.println("ID Customer: " + transaksi.getIdCustomer());
                    System.out.println("ID Mobil: " + transaksi.getIdMobil());
                    System.out.println("Tanggal Sewa: " + transaksi.getTanggal());
                    System.out.println("Total Biaya: " + transaksi.getTotalBayar());
                    System.out.println("--------------------");
                }
            }
        }

    }

    public static void next() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("\nTekan ENTER untuk lanjut");
            String terbilang = " ";
            terbilang = input.nextLine();
            System.out.print(terbilang);
            clearScreen();
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan : " + e);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}