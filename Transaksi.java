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