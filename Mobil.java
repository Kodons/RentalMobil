
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
