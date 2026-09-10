import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Screen extends JFrame implements ActionListener {
    //instance variables
    private MyArrayList<Song> playlist;
    private JTextArea listArea;
    private JLabel messageLabel;

    //user types a new song into here
    private JTextField nameField;
    private JTextField artistField;
    private JTextField albumField;
    private JTextField locationField;

    //buttons
    private JButton addButton;
    private JButton randomizeButton;

    public Screen() {
        //title
        super("My Playlist");

        playlist = new MyArrayList<Song>();
        loadStartingSongs();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // one column of two rows so the song list gets the top half and the controls get the bottom half
        setLayout(new GridLayout(2, 1));

        //print list here
        listArea = new JTextArea(14, 40);
        listArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(listArea);
        add(scroll);

        //user interaction panel
        add(buildControlPanel());

        refreshList();

        setMinimumSize(new Dimension(600, 600));
        pack();
        setSize(600, 620);
        setLocationRelativeTo(null);
    }

    private JPanel buildControlPanel() {
        // six rows stacked on top of each other with one row of controls in each
        JPanel bottom = new JPanel(new GridLayout(6, 1));

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

        // the row of buttons
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

        // partner 2 classwork
        // the sort buttons and the two delete buttons get added right here

        bottom.add(buttonPanel);

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
        if (e.getSource() == addButton) {
            addSong();
        } else if (e.getSource() == randomizeButton) {
            randomizeList();
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

    // empties the four typing boxes after a song goes in
    private void clearFields() {
        nameField.setText("");
        artistField.setText("");
        albumField.setText("");
        locationField.setText("");
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
