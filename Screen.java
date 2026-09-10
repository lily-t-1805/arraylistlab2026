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

        // partner 1 classwork #3
        // the boxes for adding a song by name artist album and location
        // every row is its own small panel holding a label and the box that goes with it
        nameField = new JTextField(20);
        artistField = new JTextField(20);
        albumField = new JTextField(20);
        locationField = new JTextField(20);

        JPanel nameRow = new JPanel();
        nameRow.add(new JLabel("song name:"));
        nameRow.add(nameField);
        bottom.add(nameRow);

        JPanel artistRow = new JPanel();
        artistRow.add(new JLabel("artist:"));
        artistRow.add(artistField);
        bottom.add(artistRow);

        JPanel albumRow = new JPanel();
        albumRow.add(new JLabel("album:"));
        albumRow.add(albumField);
        bottom.add(albumRow);

        JPanel locationRow = new JPanel();
        locationRow.add(new JLabel("location (1 to size plus one):"));
        locationRow.add(locationField);
        bottom.add(locationRow);

        // the row of buttons sits right under the boxes it uses
        JPanel buttonPanel = new JPanel();

        addButton = new JButton("add song");
        // the screen itself is the listener so no anonymous class is needed
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        // partner 1 classwork #2
        // the button that shuffles the playlist into a random order
        randomizeButton = new JButton("randomize list");
        randomizeButton.addActionListener(this);
        buttonPanel.add(randomizeButton);

        bottom.add(buttonPanel);

        // partner 2 classwork #1
        // the sort buttons
        JPanel sortPanel = new JPanel();

        sortNameButton = new JButton("sort by name");
        sortNameButton.addActionListener(this);
        sortPanel.add(sortNameButton);

        sortArtistButton = new JButton("sort by artist");
        sortArtistButton.addActionListener(this);
        sortPanel.add(sortArtistButton);

        sortAlbumButton = new JButton("sort by album");
        sortAlbumButton.addActionListener(this);
        sortPanel.add(sortAlbumButton);

        bottom.add(sortPanel);

        // partner 2 classwork #2
        // the location field and delete button
        deleteLocationField = new JTextField(10);

        JPanel deleteLocationPanel = new JPanel();
        deleteLocationPanel.add(new JLabel("delete location:"));
        deleteLocationPanel.add(deleteLocationField);

        deleteLocationButton = new JButton("delete by location");
        deleteLocationButton.addActionListener(this);
        deleteLocationPanel.add(deleteLocationButton);

        bottom.add(deleteLocationPanel);

        // partner 2 classwork #3
        // the boxes for deleting a song by name artist and album
        deleteNameField = new JTextField(10);
        deleteArtistField = new JTextField(10);
        deleteAlbumField = new JTextField(10);

        JPanel deleteSongPanel = new JPanel();

        deleteSongPanel.add(new JLabel("name:"));
        deleteSongPanel.add(deleteNameField);

        deleteSongPanel.add(new JLabel("artist:"));
        deleteSongPanel.add(deleteArtistField);

        deleteSongPanel.add(new JLabel("album:"));
        deleteSongPanel.add(deleteAlbumField);

        deleteSongButton = new JButton("delete song");
        deleteSongButton.addActionListener(this);
        deleteSongPanel.add(deleteSongButton);

        bottom.add(deleteSongPanel);

        // the last row is the note that says what just happened
        JPanel messageRow = new JPanel();
        messageLabel = new JLabel(" ");
        messageRow.add(messageLabel);
        bottom.add(messageRow);

        return bottom;
    }

    // partner 1 classwork #1
    // the playlist starts out holding these songs before the user touches anything
    private void loadStartingSongs() {
        playlist.add(new Song("Bohemian Rhapsody", "Queen", "A Night at the Opera"));
        playlist.add(new Song("Hey Jude", "The Beatles", "Hey Jude"));
        playlist.add(new Song("Superstition", "Stevie Wonder", "Talking Book"));
        playlist.add(new Song("Take On Me", "a-ha", "Hunting High and Low"));
        playlist.add(new Song("Dreams", "Fleetwood Mac", "Rumours"));
        playlist.add(new Song("Africa", "Toto", "Toto IV"));
        playlist.add(new Song("Smells Like Teen Spirit", "Nirvana", "Nevermind"));
    }

    // partner 1 classwork #1
    // wipes the text area and prints the list back out in the numbered format the lab wants
    // the numbers the user sees start at one even though the list itself starts at zero
    private void refreshList() {
        String text = "";
        if (playlist.isEmpty()) {
            text = "the playlist is empty.";
        } else {
            for (int i = 0; i < playlist.size(); i++) {
                text = text + (i + 1) + ". " + playlist.get(i) + "\n";
            }
        }
        listArea.setText(text);
        // scroll back to the top so the user always sees song number one first
        listArea.setCaretPosition(0);
    }

    // every button on the window ends up here and we figure out which one was clicked
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == addButton) {
            addSong();
        } else if (source == randomizeButton) {
            randomizeList();
        } else if (source == sortNameButton) {
            sortBy("song name");
        } else if (source == sortArtistButton) {
            sortBy("artist");
        } else if (source == sortAlbumButton) {
            sortBy("album");
        } else if (source == deleteLocationButton) {
            deleteByLocation();
        } else if (source == deleteSongButton) {
            deleteBySong();
        }
    }

    // partner 1 classwork #3
    // reads the four boxes and drops the new song into the spot the user asked for
    private void addSong() {
        String name = nameField.getText().trim();
        String artist = artistField.getText().trim();
        String album = albumField.getText().trim();
        String location = locationField.getText().trim();

        // the three song boxes all have to be filled in or there is nothing to add
        if (name.equals("") || artist.equals("") || album.equals("")) {
            showMessage("please fill in the song name, the artist, and the album.", true);
            return;
        }

        Song song = new Song(name, artist, album);

        // leaving the location blank just means stick it on the end of the list
        if (location.equals("")) {
            playlist.add(song);
            refreshList();
            clearFields();
            showMessage("added " + song + " to the end of the list.", false);
            return;
        }

        // the location has to be a number so we try to read one and complain if we cannot
        int spot = 0;
        try {
            spot = Integer.parseInt(location);
        } catch (NumberFormatException error) {
            showMessage("the location has to be a whole number.", true);
            return;
        }

        // the user counts from one so one past the end is size plus one
        if (spot < 1 || spot > playlist.size() + 1) {
            showMessage("the location has to be between 1 and " + (playlist.size() + 1) + ".", true);
            return;
        }

        // take one off of what the user typed to turn it into a real index
        playlist.add(spot - 1, song);
        refreshList();
        clearFields();
        showMessage("added " + song + " at number " + spot + ".", false);
    }

    // partner 1 classwork #2
    // shuffles the playlist by walking backwards and trading each song with a random earlier one
    private void randomizeList() {
        if (playlist.size() < 2) {
            showMessage("there are not enough songs to shuffle.", true);
            return;
        }
        for (int i = playlist.size() - 1; i > 0; i--) {
            // pick a random spot from the front of the list through spot i
            int pick = (int) (Math.random() * (i + 1));
            playlist.swap(i, pick);
        }
        refreshList();
        showMessage("the playlist was shuffled.", false);
    }

    // partner 2 classwork #1
    // sorts the playlist alphabetically by song name, artist, or album using a bubble sort
    // part says which one to sort by and it also goes into the message at the bottom
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
        showMessage("the playlist was sorted by " + part + ".", false);
    }

    // partner 2 classwork #1
    // hands back the artist or the album of a song, and the song name for anything else
    private String getPart(Song song, String part) {
        if (part.equals("artist")) {
            return song.getArtist();
        } else if (part.equals("album")) {
            return song.getAlbum();
        } else {
            return song.getName();
        }
    }

    // partner 2 classwork #2
    // deletes a song using the number that the user sees on the screen
    private void deleteByLocation() {
        String location = deleteLocationField.getText().trim();

        if (location.equals("")) {
            showMessage("please enter the location of the song to delete.", true);
            return;
        }

        int spot = 0;
        try {
            spot = Integer.parseInt(location);
        } catch (NumberFormatException error) {
            showMessage("the location has to be a whole number.", true);
            return;
        }

        // the user enters numbers starting at one
        if (spot < 1 || spot > playlist.size()) {
            showMessage("the location has to be between 1 and " + playlist.size() + ".", true);
            return;
        }

        // turn the displayed number into an array index
        Song removed = playlist.remove(spot - 1);

        refreshList();
        deleteLocationField.setText("");
        showMessage("removed " + removed + ".", false);
    }

    // partner 2 classwork #3
    // deletes a song by name artist and album
    // this must use the remove(Object) method from MyArrayList
    private void deleteBySong() {
        String name = deleteNameField.getText().trim();
        String artist = deleteArtistField.getText().trim();
        String album = deleteAlbumField.getText().trim();

        if (name.equals("") || artist.equals("") || album.equals("")) {
            showMessage("please fill in the song name, artist, and album.", true);
            return;
        }

        Song song = new Song(name, artist, album);

        // use remove(Object) as required by the lab
        if (playlist.remove(song)) {
            refreshList();
            clearDeleteFields();
            showMessage("removed " + song + ".", false);
        } else {
            showMessage("that song was not found in the playlist.", true);
        }
    }

    // empties the four typing boxes after a song goes in
    private void clearFields() {
        nameField.setText("");
        artistField.setText("");
        albumField.setText("");
        locationField.setText("");
    }

    // empties the boxes used to delete a song
    private void clearDeleteFields() {
        deleteNameField.setText("");
        deleteArtistField.setText("");
        deleteAlbumField.setText("");
    }

    // puts a note at the bottom of the window in red when something went wrong
    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setForeground(Color.RED);
        } else {
            messageLabel.setForeground(new Color(0, 120, 0));
        }
    }
}
