
import java.util.*;

// Class Mobil
class Mobil {

    private String id;
    private String nama;
    private String status;
    private double hargaSewa;

    public Mobil(String id, String nama, String status, double hargaSewa) {
        this.id = id;
        this.nama = nama;
        this.status = status;
        this.hargaSewa = hargaSewa;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
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
}

// Class Pelanggan
class Pelanggan {

    private String id;
    private String nama;
    private String password;

    public Pelanggan(String id, String nama, String password) {
        this.id = id;
        this.nama = nama;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }
}

// Class Transaksi
class Transaksi {

    private String idTransaksi;
    private String idPelanggan;
    private String idMobil;
    private double totalBayar;
    private String tanggal;

    public Transaksi(String idTransaksi, String idPelanggan, String idMobil, double totalBayar, String tanggal) {
        this.idTransaksi = idTransaksi;
        this.idPelanggan = idPelanggan;
        this.idMobil = idMobil;
        this.totalBayar = totalBayar;
        this.tanggal = tanggal;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdMobil() {
        return idMobil;
    }

    public double getTotalBayar() {
        return totalBayar;
    }

    public String getTanggal() {
        return tanggal;
    }
}

// Class Graph (contoh representasi hubungan mobil dengan lokasi penempatan)
class Graph {

    private Map<String, List<String>> adjacencyList = new HashMap<>();

    public void addVertex(String lokasi) {
        adjacencyList.putIfAbsent(lokasi, new ArrayList<>());
    }

    public void addEdge(String lokasi1, String lokasi2) {
        adjacencyList.get(lokasi1).add(lokasi2);
        adjacencyList.get(lokasi2).add(lokasi1);
    }

    public List<String> getConnections(String lokasi) {
        return adjacencyList.getOrDefault(lokasi, new ArrayList<>());
    }
}

// Main Application Class
public class RentalMobilApp {

    private static TreeMap<String, Mobil> mobilTree = new TreeMap<>();
    private static ArrayList<Pelanggan> pelangganList = new ArrayList<>();
    private static ArrayList<Transaksi> transaksiList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        mainMenu();
    }

    private static void seedData() {
        mobilTree.put("001", new Mobil("001", "Toyota Avanza", "Tersedia", 300000));
        mobilTree.put("002", new Mobil("002", "Honda Civic", "Tersedia", 500000));
        mobilTree.put("003", new Mobil("003", "Suzuki Ertiga", "Tersedia", 350000));

        pelangganList.add(new Pelanggan("001", "Budi", "1234"));
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== Rental Mobil ===");
            System.out.println("1. Login Admin");
            System.out.println("2. Login Customer");
            System.out.println("3. Registrasi Customer");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerLogin();
                    break;
                case 3:
                    registerCustomer();
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        }
    }

    private static void adminMenu() {
        System.out.print("Masukkan password admin: ");
        String password = scanner.nextLine();

        if (!password.equals("admin123")) {
            System.out.println("Password salah!");
            return;
        }

        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Kelola Data Kendaraan");
            System.out.println("2. Kelola Data Pelanggan");
            System.out.println("3. Laporan Transaksi dan Pendapatan");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
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

    private static void manageVehicles() {
        while (true) {
            System.out.println("\n=== Kelola Kendaraan ===");
            System.out.println("1. Tampilkan Semua Kendaraan");
            System.out.println("2. Tambah Kendaraan");
            System.out.println("3. Hapus Kendaraan");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
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

    private static void displayVehicles() {
        System.out.println("\n=== Daftar Kendaraan ===");
        for (Mobil mobil : mobilTree.values()) {
            System.out.printf("ID: %s, Nama: %s, Status: %s, Harga: %.2f\n", mobil.getId(), mobil.getNama(), mobil.getStatus(), mobil.getHargaSewa());
        }
    }

    private static void addVehicle() {
        System.out.print("Masukkan ID Kendaraan: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Kendaraan: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Harga Sewa: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        mobilTree.put(id, new Mobil(id, nama, "Tersedia", harga));
        System.out.println("Kendaraan berhasil ditambahkan!");
    }

    private static void deleteVehicle() {
        System.out.print("Masukkan ID Kendaraan yang akan dihapus: ");
        String id = scanner.nextLine();

        if (mobilTree.remove(id) != null) {
            System.out.println("Kendaraan berhasil dihapus!");
        } else {
            System.out.println("Kendaraan tidak ditemukan!");
        }
    }

    private static void manageCustomers() {
        System.out.println("\n=== Daftar Pelanggan ===");
        for (Pelanggan p : pelangganList) {
            System.out.printf("ID: %s, Nama: %s\n", p.getId(), p.getNama());
        }
    }

    private static void generateReport() {
        System.out.println("\n=== Laporan Transaksi ===");
        double totalPendapatan = 0;
        for (Transaksi t : transaksiList) {
            System.out.printf("ID Transaksi: %s, Pelanggan: %s, Mobil: %s, Total: %.2f, Tanggal: %s\n",
                    t.getIdTransaksi(), t.getIdPelanggan(), t.getIdMobil(), t.getTotalBayar(), t.getTanggal());
            totalPendapatan += t.getTotalBayar();
        }
        System.out.printf("Total Pendapatan: %.2f\n", totalPendapatan);
    }

    private static void registerCustomer() {
        System.out.print("Masukkan ID Pelanggan: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Nama Pelanggan: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        pelangganList.add(new Pelanggan(id, nama, password));
        System.out.println("Registrasi berhasil! Pelanggan telah ditambahkan.");
    }

    private static void customerMenu(Pelanggan pelanggan) {
        while (true) {
            System.out.println("\n=== Menu Customer ===");
            System.out.println("1. Lihat Daftar Mobil");
            System.out.println("2. Sewa Mobil");
            System.out.println("3. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1 -> displayAvailableVehicles();
                case 2 -> rentCar(pelanggan);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }
    
    private static void displayAvailableVehicles() {
        System.out.println("\n=== Daftar Mobil Tersedia ===");
        for (Mobil mobil : mobilTree.values()) {
            if (mobil.getStatus().equals("Tersedia")) {
                System.out.printf("ID: %s, Nama: %s, Harga per Hari: Rp%.2f\n",
                        mobil.getId(), mobil.getNama(), mobil.getHargaSewa());
            }
        }
    }
    
    private static void rentCar(Pelanggan pelanggan) {
        // Tampilkan mobil yang tersedia
        displayAvailableVehicles();
        
        // Pilih mobil
        System.out.print("Masukkan ID mobil yang ingin disewa: ");
        String idMobil = scanner.nextLine();
        
        Mobil selectedCar = mobilTree.get(idMobil);
        if (selectedCar == null || !selectedCar.getStatus().equals("Tersedia")) {
            System.out.println("Mobil tidak tersedia!");
            return;
        }
        
        // Input tanggal sewa
        System.out.print("Masukkan tanggal mulai sewa (DD/MM/YYYY): ");
        String tanggalMulai = scanner.nextLine();
        System.out.print("Masukkan jumlah hari sewa: ");
        int jumlahHari = scanner.nextInt();
        scanner.nextLine();
        
        // Hitung total pembayaran
        double totalBayar = selectedCar.getHargaSewa() * jumlahHari;
        
        // Tampilkan detail pembayaran
        System.out.println("\n=== Detail Pembayaran ===");
        System.out.println("Mobil: " + selectedCar.getNama());
        System.out.println("Harga per hari: Rp" + selectedCar.getHargaSewa());
        System.out.println("Jumlah hari: " + jumlahHari);
        System.out.println("Total pembayaran: Rp" + totalBayar);
        
        // Konfirmasi pembayaran
        System.out.print("Konfirmasi pembayaran (Y/N)? ");
        String konfirmasi = scanner.nextLine();
        
        if (konfirmasi.equalsIgnoreCase("Y")) {
            // Generate ID transaksi
            String idTransaksi = "TRX" + System.currentTimeMillis();
            
            // Buat transaksi baru
            Transaksi transaksi = new Transaksi(
                idTransaksi,
                pelanggan.getId(),
                selectedCar.getId(),
                totalBayar,
                tanggalMulai
            );
            
            // Update status mobil
            selectedCar.setStatus("Disewa");
            
            // Tambahkan ke list transaksi
            transaksiList.add(transaksi);
            
            System.out.println("Pembayaran berhasil!");
            System.out.println("ID Transaksi: " + idTransaksi);
        } else {
            System.out.println("Pembayaran dibatalkan.");
        }
    }
    
    // Update method customerLogin to use the new customerMenu
    private static void customerLogin() {
        System.out.print("Masukkan ID Pelanggan: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();
    
        for (Pelanggan pelanggan : pelangganList) {
            if (pelanggan.getId().equals(id) && pelanggan.getPassword().equals(password)) {
                System.out.println("Login berhasil! Selamat datang, " + pelanggan.getNama());
                customerMenu(pelanggan);  // Call the customer menu after successful login
                return;
            }
        }
        System.out.println("ID atau Password salah!");
    }
}
