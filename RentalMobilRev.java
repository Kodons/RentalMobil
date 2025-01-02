
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class RentalMobil {

    static class NodeMobil {

        Mobil data;
        NodeMobil next;

        NodeMobil(Mobil data) {
            this.data = data;
        }
    }

    static class NodeTransaksi {

        Transaksi data;
        NodeTransaksi next;
        NodeMobil mobilNode;

        NodeTransaksi(Transaksi data, NodeMobil mobilNode) {
            this.data = data;
            this.mobilNode = mobilNode;
        }
    }

    static class Mobil {

        private String id;
        private String nama;
        private String tipe;
        private double hargaSewa;
        private boolean disewakan;

        Mobil(String id, String nama, String tipe, double hargaSewa) {
            this.id = id;
            this.nama = nama;
            this.tipe = tipe;
            this.hargaSewa = hargaSewa;
            this.disewakan = false;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTipe() {
            return tipe;
        }

        public void setTipe(String tipe) {
            this.tipe = tipe;
        }

        public double getHargaSewa() {
            return hargaSewa;
        }

        public void setHargaSewa(double hargaSewa) {
            this.hargaSewa = hargaSewa;
        }

        public boolean isDisewakan() {
            return disewakan;
        }

        public void setDisewakan(boolean disewakan) {
            this.disewakan = disewakan;
        }
    }

    static class Transaksi {

        private String customerId;
        private String tanggal;
        private double totalHarga;

        Transaksi(String customerId, String tanggal, double totalHarga) {
            this.customerId = customerId;
            this.tanggal = tanggal;
            this.totalHarga = totalHarga;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public double getTotalHarga() {
            return totalHarga;
        }

        public void setTotalHarga(double totalHarga) {
            this.totalHarga = totalHarga;
        }
    }

    static class Admin {

        NodeMobil headMobil = null;
        NodeTransaksi headTransaksi = null;

        void tambahMobil(String id, String nama, String tipe, double hargaSewa) {
            NodeMobil current = headMobil;
            while (current != null) {
                if (current.data.getId().equals(id)) {
                    System.out.println("\nGagal menambahkan mobil. ID sudah ada.");
                    next();
                    return;
                }
                current = current.next;
            }
            Mobil mobilBaru = new Mobil(id, nama, tipe, hargaSewa);
            NodeMobil nodeMobilBaru = new NodeMobil(mobilBaru);
            nodeMobilBaru.next = headMobil;
            headMobil = nodeMobilBaru;
            System.out.println("\nMobil berhasil ditambahkan.");
        }

        void editMobil(String id, String namaBaru, String tipeBaru, double hargaBaru) {
            NodeMobil current = headMobil;
            while (current != null) {
                if (current.data.getId().equals(id)) {
                    current.data.setNama(namaBaru);
                    current.data.setTipe(tipeBaru);
                    current.data.setHargaSewa(hargaBaru);
                    System.out.println("\nMobil berhasil diperbarui.");
                    next();
                    return;
                }
                current = current.next;
            }
            System.out.println("\nMobil tidak ditemukan.");
            next();
        }

        void hapusMobil(String id) {
            NodeMobil current = headMobil, prev = null;
            while (current != null && !current.data.getId().equals(id)) {
                prev = current;
                current = current.next;
            }
            if (current == null) {
                System.out.println("Mobil tidak ditemukan.");
                return;
            }
            if (prev == null) {
                headMobil = current.next;
            } else {
                prev.next = current.next;
            }
            System.out.println("Mobil berhasil dihapus.");
        }

        void tampilkanMobil() {
            NodeMobil current = headMobil;
            clearScreen();
            System.out.println("Daftar Mobil:");
            while (current != null) {
                Mobil mobil = current.data;
                System.out.println("ID: " + mobil.getId() + ", Nama: " + mobil.getNama() + ", Tipe: " + mobil.getTipe() + ", Harga Sewa: " + mobil.getHargaSewa() + ", Status: " + (mobil.isDisewakan() ? "Disewakan" : "Tersedia"));
                current = current.next;
            }
        }

        void tampilkanRiwayatTransaksi() {
            NodeTransaksi current = headTransaksi;
            clearScreen();
            System.out.println("Riwayat Transaksi:");
            while (current != null) {
                Transaksi transaksi = current.data;
                Mobil mobil = current.mobilNode.data;
                System.out.println("Customer ID: " + transaksi.getCustomerId() + ", Mobil: " + mobil.getNama() + ", Tanggal: " + transaksi.getTanggal() + ", Total Harga: " + transaksi.getTotalHarga());
                current = current.next;
            }
        }

        void laporanKeuanganBulanan() {
            NodeTransaksi current = headTransaksi;
            double totalPendapatan = 0;
            clearScreen();
            while (current != null) {
                totalPendapatan += current.data.getTotalHarga();
                current = current.next;
            }
            System.out.println("Total Pendapatan Bulanan: " + totalPendapatan);
        }
    }

    static class NodeCustomer {

        String username;
        String password;
        NodeCustomer next;

        NodeCustomer(String username, String password) {
            this.username = username;
            this.password = password;
            this.next = null;
        }
    }

    static class Customer {

        private String id;
        private Admin admin;

        Customer(String id, Admin admin) {
            this.id = id;
            this.admin = admin;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }

        void lihatMobil() {
            NodeMobil current = admin.headMobil;

            clearScreen();
            System.out.println("Daftar Mobil Tersedia:");
            while (current != null) {
                Mobil mobil = current.data;
                if (!mobil.isDisewakan()) {
                    System.out.println("ID: " + mobil.getId() + ", Nama: " + mobil.getNama() + ", Tipe: " + mobil.getTipe() + ", Harga Sewa: " + mobil.getHargaSewa());
                }
                current = current.next;
            }
        }

        void sewaMobil(String mobilId, String tanggal, int durasi) {
            if (!isValidDate(tanggal)) {
                System.out.println("Tanggal tidak valid. Gunakan format YYYY-MM-DD.");
                return;
            }

            if (durasi <= 0) {
                System.out.println("Durasi penyewaan harus lebih dari 0 hari.");
                return;
            }
            NodeMobil current = admin.headMobil;
            while (current != null && !current.data.getId().equals(mobilId)) {
                current = current.next;
            }
            if (current == null) {
                System.out.println("Mobil tidak ditemukan.");
                return;
            }
            Mobil mobil = current.data;
            if (mobil.isDisewakan()) {
                System.out.println("Mobil sedang disewakan.");
                return;
            }
            mobil.setDisewakan(true);
            double totalHarga = mobil.getHargaSewa() * durasi;
            Transaksi transaksiBaru = new Transaksi(id, tanggal, totalHarga);
            NodeTransaksi nodeTransaksiBaru = new NodeTransaksi(transaksiBaru, current);
            nodeTransaksiBaru.next = admin.headTransaksi;
            admin.headTransaksi = nodeTransaksiBaru;
            System.out.println("Penyewaan berhasil. Total Harga: " + totalHarga);
        }

        boolean isValidDate(String date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        void kembalikanMobil(String mobilId) {
            NodeMobil current = admin.headMobil;

            clearScreen();
            while (current != null && !current.data.getId().equals(mobilId)) {
                current = current.next;
            }
            if (current == null) {
                System.out.println("Mobil tidak ditemukan.");
                next();
                return;
            }
            Mobil mobil = current.data;
            if (!mobil.isDisewakan()) {
                System.out.println("Mobil ini belum disewakan.");
                next();
                return;
            }
            mobil.setDisewakan(false);
            System.out.println("Mobil berhasil dikembalikan. Terima kasih telah menggunakan layanan kami.");
            next();
        }

        void lihatRiwayatTransaksi() {
            NodeTransaksi current = admin.headTransaksi;

            clearScreen();
            System.out.println("Riwayat Transaksi Anda:");
            while (current != null) {
                Transaksi transaksi = current.data;
                Mobil mobil = current.mobilNode.data;
                if (transaksi.getCustomerId().equals(id)) {
                    System.out.println("Mobil: " + mobil.getNama() + ", Tanggal: " + transaksi.getTanggal() + ", Total Harga: " + transaksi.getTotalHarga());
                }
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        NodeCustomer headCustomer = null;

        admin.tambahMobil("M001", "Toyota Avanza", "MPV", 350000);
        admin.tambahMobil("M002", "Honda Jazz", "Hatchback", 400000);
        admin.tambahMobil("M003", "Suzuki Ertiga", "MPV", 370000);

        while (true) {
            tampilkanMenuUtama();
            System.out.print("\nInput : ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 ->
                    loginAdmin(scanner, admin);
                case 2 -> {
                    System.out.print("Masukkan Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Masukkan Password: ");
                    String password = scanner.nextLine();
                    if (cariCustomer(headCustomer, username, password)) {
                        System.out.println("Login Berhasil!");
                        menuCustomer(scanner, admin, username);
                    } else {
                        System.out.println("Username atau Password salah.");
                    }
                }
                case 3 -> {
                    System.out.print("Masukkan Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Masukkan Password: ");
                    String password = scanner.nextLine();
                    headCustomer = tambahCustomer(headCustomer, username, password);
                }
                case 4 -> {
                    System.out.println("Keluar dari aplikasi.");
                    return;
                }
                default ->
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    static NodeCustomer tambahCustomer(NodeCustomer head, String username, String password) {
        if (cariCustomer(head, username)) {
            System.out.println("Username sudah digunakan.");
            return head;
        }
        NodeCustomer newCustomer = new NodeCustomer(username, password);
        newCustomer.next = head;
        return newCustomer;
    }

    static boolean cariCustomer(NodeCustomer head, String username, String password) {
        NodeCustomer current = head;
        while (current != null) {
            if (current.username.equals(username) && current.password.equals(password)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    static boolean cariCustomer(NodeCustomer head, String username) {
        NodeCustomer current = head;
        while (current != null) {
            if (current.username.equals(username)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private static void tampilkanMenuUtama() {
        clearScreen();
        System.out.println("1. Login sebagai Admin");
        System.out.println("2. Login sebagai Customer");
        System.out.println("3. Registrasi Customer");
        System.out.println("4. Keluar");
    }

    private static void loginAdmin(Scanner scanner, Admin admin) {
        clearScreen();
        System.out.print("Masukkan Username Admin: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan Password Admin: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("admin")) {
            boolean isRunning = true;
            while (isRunning) {
                tampilkanMenuAdmin();
                System.out.print("\nInput : ");
                int adminPilihan = scanner.nextInt();
                scanner.nextLine();

                switch (adminPilihan) {
                    case 1 ->
                        tambahMobil(scanner, admin);
                    case 2 ->
                        hapusMobil(scanner, admin);
                    case 3 ->
                        admin.tampilkanMobil();
                    case 4 ->
                        editMobil(scanner, admin);
                    case 5 ->
                        admin.tampilkanRiwayatTransaksi();
                    case 6 ->
                        admin.laporanKeuanganBulanan();
                    case 7 ->
                        isRunning = false;
                    default ->
                        System.out.println("Pilihan tidak valid.");
                }
            }
        } else {
            System.out.println("Username atau password admin salah.");
        }
    }

    private static void tampilkanMenuAdmin() {
        System.out.println("\n1. Tambah Mobil");
        System.out.println("2. Hapus Mobil");
        System.out.println("3. Lihat Daftar Mobil");
        System.out.println("4. Update Mobil");
        System.out.println("5. Lihat Riwayat Transaksi");
        System.out.println("6. Laporan Keuangan");
        System.out.println("7. Logout");
    }

    private static void tambahMobil(Scanner scanner, Admin admin) {
        clearScreen();
        System.out.print("Masukkan ID Mobil: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Mobil: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Tipe Mobil: ");
        String tipe = scanner.nextLine();
        System.out.print("Masukkan Harga Sewa: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        admin.tambahMobil(id, nama, tipe, harga);
    }

    private static void editMobil(Scanner scanner, Admin admin) {
        clearScreen();
        System.out.print("Masukkan ID Mobil yang akan diedit: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Baru Mobil: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Tipe Baru Mobil: ");
        String tipe = scanner.nextLine();
        System.out.print("Masukkan Harga Sewa Baru: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        admin.editMobil(id, nama, tipe, harga);
    }

    private static void hapusMobil(Scanner scanner, Admin admin) {
        clearScreen();
        System.out.print("Masukkan ID Mobil yang akan dihapus: ");
        String id = scanner.nextLine();
        admin.hapusMobil(id);
    }

    static void menuCustomer(Scanner scanner, Admin admin, String username) {
        Customer customer = new Customer(username, admin);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. Lihat Daftar Mobil");
            System.out.println("2. Sewa Mobil");
            System.out.println("3. Kembalikan Mobil");
            System.out.println("4. Lihat Riwayat Transaksi");
            System.out.println("5. Logout");
            System.out.print("Pilih: ");
            int customerPilihan = scanner.nextInt();
            scanner.nextLine();

            switch (customerPilihan) {
                case 1 ->
                    customer.lihatMobil();
                case 2 -> {
                    System.out.print("Masukkan ID Mobil: ");
                    String mobilId = scanner.nextLine();
                    System.out.print("Masukkan Tanggal (YYYY-MM-DD): ");
                    String tanggal = scanner.nextLine();
                    System.out.print("Masukkan Durasi (hari): ");
                    int durasi = scanner.nextInt();
                    scanner.nextLine();
                    customer.sewaMobil(mobilId, tanggal, durasi);
                }
                case 3 -> {
                    System.out.print("Masukkan ID Mobil yang akan dikembalikan: ");
                    String mobilId = scanner.nextLine();
                    customer.kembalikanMobil(mobilId);
                }
                case 4 ->
                    customer.lihatRiwayatTransaksi();
                case 5 ->
                    isRunning = false;
                default ->
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // private static void tampilkanMenuCustomer() {
    //     System.out.println("\n1. Lihat Daftar Mobil");
    //     System.out.println("2. Sewa Mobil");
    //     System.out.println("3. Kembalikan Mobil");
    //     System.out.println("4. Lihat Riwayat Transaksi");
    //     System.out.println("5. Logout");
    // }

    // private static void sewaMobil(Scanner scanner, Customer customer) {
    //     clearScreen();
    //     System.out.print("Masukkan ID Mobil: ");
    //     String mobilId = scanner.nextLine();
    //     System.out.print("Masukkan Tanggal (YYYY-MM-DD): ");
    //     String tanggal = scanner.nextLine();
    //     System.out.print("Masukkan Durasi (hari): ");
    //     int durasi = scanner.nextInt();
    //     scanner.nextLine();
    //     customer.sewaMobil(mobilId, tanggal, durasi);
    // }

    // private static void registrasiCustomer(Scanner scanner, Map<String, String> customerAccounts) {
    //     clearScreen();
    //     System.out.print("Masukkan Username Baru: ");
    //     String username = scanner.nextLine();
    //     System.out.print("Masukkan Password Baru: ");
    //     String password = scanner.nextLine();

    //     if (!customerAccounts.containsKey(username)) {
    //         customerAccounts.put(username, password);
    //         System.out.println("\nRegistrasi berhasil! Silakan login sebagai customer.");
    //         next();
    //     } else {
    //         System.out.println("\nUsername sudah digunakan. Coba username lain.");
    //         next();
    //     }
    // }

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
