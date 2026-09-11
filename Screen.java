import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Screen extends JFrame implements ActionListener {
    // instance variables
    private MyArrayList<Song> playlist;
    private JTextArea listArea;
    private JLabel messageLabel;

    // user types a new song into here
    private JTextField nameField;
    private JTextField artistField;
    private JTextField albumField;
    private JTextField locationField;

    // fields for deleting a song
    private JTextField deleteNameField;
    private JTextField deleteArtistField;
    private JTextField deleteAlbumField;

    // field for deleting by location
    private JTextField deleteLocationField;

    // buttons
    private JButton addButton;
    private JButton randomizeButton;

    // partner 2 buttons
    private JButton sortNameButton;
    private JButton sortArtistButton;
    private JButton sortAlbumButton;
    private JButton deleteLocationButton;
    private JButton deleteSongButton;

    public Screen() {
        // title
        super("My Playlist");

        playlist = new MyArrayList<Song>();
        loadStartingSongs();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // one column of two rows so the song list gets the top half and the controls get the bottom half
        setLayout(new GridLayout(2, 1));

        // print list here
        listArea = new JTextArea();
        listArea.setEditable(false);
        add(new JScrollPane(listArea));

        // user interaction panel
        add(buildControlPanel());

        refreshList();

        // big enough that every row of controls fits without getting cut off
        setSize(700, 760);
        setLocationRelativeTo(null);
    }

    private JPanel buildControlPanel() {
        // nine rows stacked on top of each other with one row of controls in each
        JPanel bottom = new JPanel(new GridLayout(9, 1));

        // the boxes for adding a song by name artist album and location
        nameField = new JTextField(20);
        artistField = new JTextField(20);
        albumField = new JTextField(20);
        locationField = new JTextField(20);

        JPanel nameRow = new JPanel();
        nameRow.add(new JLabel("Song name:"));
        nameRow.add(nameField);
        bottom.add(nameRow);

        JPanel artistRow = new JPanel();
        artistRow.add(new JLabel("Artist:"));
        artistRow.add(artistField);
        bottom.add(artistRow);

        JPanel albumRow = new JPanel();
        albumRow.add(new JLabel("Album:"));
        albumRow.add(albumField);
        bottom.add(albumRow);

        JPanel locationRow = new JPanel();
        locationRow.add(new JLabel("Location:"));
        locationRow.add(locationField);
        bottom.add(locationRow);

        //buttons
        JPanel buttonPanel = new JPanel();

        addButton = new JButton("Add song");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        // the button that shuffles the playlist into a random order
        randomizeButton = new JButton("Randomize list");
        randomizeButton.addActionListener(this);
        buttonPanel.add(randomizeButton);

        bottom.add(buttonPanel);

        // the sort buttons
        JPanel sortPanel = new JPanel();

        sortNameButton = new JButton("Sort by name");
        sortNameButton.addActionListener(this);
        sortPanel.add(sortNameButton);

        sortArtistButton = new JButton("Sort by artist");
        sortArtistButton.addActionListener(this);
        sortPanel.add(sortArtistButton);

        sortAlbumButton = new JButton("Sort by album");
        sortAlbumButton.addActionListener(this);
        sortPanel.add(sortAlbumButton);

        bottom.add(sortPanel);

        //the location field and delete button
        deleteLocationField = new JTextField(10);

        JPanel deleteLocationPanel = new JPanel();
        deleteLocationPanel.add(new JLabel("Delete location:"));
        deleteLocationPanel.add(deleteLocationField);

        deleteLocationButton = new JButton("Delete by location");
        deleteLocationButton.addActionListener(this);
        deleteLocationPanel.add(deleteLocationButton);

        bottom.add(deleteLocationPanel);

        //the boxes for deleting a song by name artist and album
        deleteNameField = new JTextField(10);
        deleteArtistField = new JTextField(10);
        deleteAlbumField = new JTextField(10);

        JPanel deleteSongPanel = new JPanel();

        deleteSongPanel.add(new JLabel("Name:"));
        deleteSongPanel.add(deleteNameField);

        deleteSongPanel.add(new JLabel("Artist:"));
        deleteSongPanel.add(deleteArtistField);

        deleteSongPanel.add(new JLabel("Album:"));
        deleteSongPanel.add(deleteAlbumField);

        deleteSongButton = new JButton("Delete song");
        deleteSongButton.addActionListener(this);
        deleteSongPanel.add(deleteSongButton);

        bottom.add(deleteSongPanel);

        //message to user
        JPanel messageRow = new JPanel();
        messageLabel = new JLabel(" ");
        messageRow.add(messageLabel);
        bottom.add(messageRow);

        return bottom;
    }

    //the playlist starts out holding these songs before the user touches anything
    private void loadStartingSongs() {
        playlist.add(new Song("Bohemian Rhapsody", "Queen", "A Night at the Opera"));
        playlist.add(new Song("Hey Jude", "The Beatles", "Hey Jude"));
        playlist.add(new Song("Superstition", "Stevie Wonder", "Talking Book"));
        playlist.add(new Song("Take On Me", "a-ha", "Hunting High and Low"));
        playlist.add(new Song("Dreams", "Fleetwood Mac", "Rumours"));
        playlist.add(new Song("Africa", "Toto", "Toto IV"));
        playlist.add(new Song("Smells Like Teen Spirit", "Nirvana", "Nevermind"));
    }

    private void refreshList() {
        //wipes the text area
        String text = ""; 
        if (playlist.isEmpty()) {
            text = "The playlist is empty.";
        } else {
            //prints the list back out in the numbered format
            for (int i = 0; i < playlist.size(); i++) {
                //list on screen starts at 1 
                text = text + (i + 1) + ". " + playlist.get(i) + "\n";
            }
        }
        listArea.setText(text);
        //scroll back to the top so the user always sees song number one first
        listArea.setCaretPosition(0);
    }

    //button actions
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addButton) {
            addSong();
        } 
        else if (source == randomizeButton) {
            randomizeList();
        } 
        else if (source == sortNameButton) {
            sortBy("Song name");
        } 
        else if (source == sortArtistButton) {
            sortBy("Artist");
        } 
        else if (source == sortAlbumButton) {
            sortBy("Album");
        } 
        else if (source == deleteLocationButton) {
            deleteByLocation();
        } 
        else if (source == deleteSongButton) {
            deleteBySong();
        }
    }

    //add a new song
    private void addSong() {
        String name = nameField.getText().trim();
        String artist = artistField.getText().trim();
        String album = albumField.getText().trim();
        String location = locationField.getText().trim();

        //all 3 boxes have to be filled in 
        if (name.equals("") || artist.equals("") || album.equals("")) {
            showMessage("Please fill in the song name, the artist, and the album.", true);
            return;
        }

        Song song = new Song(name, artist, album);

        //leaving the location blank just means stick it on the end of the list
        if (location.equals("")) {
            playlist.add(song);
            refreshList();
            clearFields();
            showMessage("Added " + song + " to the end of the list.", false);
            return;
        }

        //check location
        int spot = 0;
        try {
            spot = Integer.parseInt(location); //get what the spot is
        } catch (NumberFormatException error) {
            showMessage("The location has to be a whole number.", true);
            return;
        }

        //spot / index out of bounds
        if (spot < 1 || spot > playlist.size() + 1) {
            showMessage("The location has to be between 1 and " + (playlist.size() + 1) + ".", true);
            return;
        }

        //add song
        playlist.add(spot - 1, song);
        refreshList();
        clearFields();
        showMessage("Added " + song + " at number " + spot + ".", false);
    }

    //shuffles the playlist by walking backwards and trading each song with a random earlier one
    private void randomizeList() {
        //has to be shuffle-able 
        if (playlist.size() < 2) {
            showMessage("There are not enough songs to shuffle.", true);
            return;
        }

        for (int i = playlist.size() - 1; i > 0; i--) {
            //swap --> pick random pos in the front
            int pick = (int) (Math.random() * (i + 1));
            playlist.swap(i, pick);
        }

        refreshList();
        showMessage("The playlist was shuffled.", false);
    }

    //sorts the playlist alphabetically by song name, artist, or album (part) using a bubble sort
    private void sortBy(String part) {
        for (int i = 0; i < playlist.size() - 1; i++) {
            for (int j = 0; j < playlist.size() - 1 - i; j++) {
                String first = getPart(playlist.get(j), part);
                String second = getPart(playlist.get(j + 1), part);

                if (first.compareToIgnoreCase(second) > 0) {
                    playlist.swap(j, j + 1);
                }
            }
        }

        refreshList();
        showMessage("The playlist was sorted by " + part + ".", false);
    }

    //hands back the artist or the album of a song, and the song name for anything else
    private String getPart(Song song, String part) {
        if (part.equalsIgnoreCase("artist")) {
            return song.getArtist();
        } 
        else if (part.equalsIgnoreCase("album")) {
            return song.getAlbum();
        } 
        else {
            return song.getName();
        }
    }

    //deletes a song using location
    private void deleteByLocation() {
        String location = deleteLocationField.getText().trim();

        //empty
        if (location.equals("")) {
            showMessage("Please enter the location of the song to delete.", true);
            return;
        }

        int spot = 0;
        try {
            spot = Integer.parseInt(location); //get location
        } catch (NumberFormatException error) {
            showMessage("The location has to be a whole number.", true);
            return;
        }

        //out of bounds
        if (spot < 1 || spot > playlist.size()) {
            showMessage("The location has to be between 1 and " + playlist.size() + ".", true);
            return;
        }

        //remove song
        Song removed = playlist.remove(spot - 1);

        refreshList();
        deleteLocationField.setText("");
        showMessage("Removed " + removed + ".", false);
    }

    //deletes a song by name artist and album
    private void deleteBySong() {
        String name = deleteNameField.getText().trim();
        String artist = deleteArtistField.getText().trim();
        String album = deleteAlbumField.getText().trim();

        if (name.equals("") || artist.equals("") || album.equals("")) {
            showMessage("Please fill in the song name, artist, and album.", true);
            return;
        }

        Song song = new Song(name, artist, album);

        //remove
        if (playlist.remove(song)) {
            refreshList();
            clearDeleteFields();
            showMessage("Removed " + song + ".", false);
        } else {
            showMessage("That song was not found in the playlist.", true);
        }
    }

    //clears
    private void clearFields() {
        nameField.setText("");
        artistField.setText("");
        albumField.setText("");
        locationField.setText("");
    }

    //empty text boxes
    private void clearDeleteFields() {
        deleteNameField.setText("");
        deleteArtistField.setText("");
        deleteAlbumField.setText("");
    }

    //message --> red if isError = true (just for design)
    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setForeground(Color.RED);
        } else {
            messageLabel.setForeground(new Color(0, 120, 0));
        }
    }
}
