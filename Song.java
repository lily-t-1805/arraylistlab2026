public class Song {
    //instance variables
    private String name;
    private String artist;
    private String album;

    //constructor
    public Song(String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    //getter methods
    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    //setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    //true only when everything matches
    //deleting a song by typing it in uses this through remove(Object)
    public boolean equals(Object o) {
        Song other = (Song) o;
        
        if (name.equalsIgnoreCase(other.getName()) && artist.equalsIgnoreCase(other.getArtist()) && album.equalsIgnoreCase(other.getAlbum())) {
            return true;
        } else {
            return false;
        }
    }

    //print song name, artist, album
    public String toString() {
        return name + ", " + artist + ", " + album;
    }
}
