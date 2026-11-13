package org.example.ui;

import org.example.model.SeasonForecast;
import org.example.service.FourSeasonService;
import org.example.service.SeasonDataService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarFrame extends JFrame {
    private final Color ink = new Color(35, 45, 60);
    private final Color muted = new Color(110, 120, 135);
    private final Color accent = new Color(57, 105, 170);
    private final Color panel = new Color(247, 249, 252);

    private final SeasonDataService seasonDataService = new SeasonDataService();
    private final FourSeasonService fourSeasonService = new FourSeasonService();

    private static final YearMonth MIN_MONTH = YearMonth.of(2001, 1);
    private static final YearMonth MAX_MONTH = YearMonth.of(2050, 12);

    private YearMonth displayedMonth = clampMonth(YearMonth.now());
    private LocalDate selectedDate = clampDate(LocalDate.now());

    private final JComboBox<String> modeCombo =
            new JComboBox<>(new String[]{"Musim Indonesia", "Empat Musim"});
    private final JComboBox<String> locationCombo =
            new JComboBox<>(new String[]{"Palembang", "Jakarta"});
    private final JComboBox<String> hemisphereCombo =
            new JComboBox<>(new String[]{"Belahan Bumi Utara", "Belahan Bumi Selatan"});

    private final JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel selectedDateLabel = new JLabel();
    private final JTextArea seasonLabel = new JTextArea();
    private final JTextArea descriptionArea = new JTextArea();
    private final JTextArea periodLabel = new JTextArea();
    private final JTextArea sourceLabel = new JTextArea();
    private final JButton sourceButton = new JButton("Buka sumber BMKG");
    private final JButton previousMonthButton = navButton("‹");
    private final JButton nextMonthButton = navButton("›");
    private final JPanel daysPanel = new JPanel(new GridLayout(0, 7, 7, 7));
    private final JPanel infoCard = new JPanel();

    public CalendarFrame() {
        setTitle("Seasonal Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 680));
        setSize(1040, 740);
        setLocationRelativeTo(null);

        buildInterface();
        attachEvents();
        refreshCalendar();
    }

    private void buildInterface() {
        JPanel root = new JPanel(new BorderLayout(22, 18));
        root.setBackground(Color.WHITE);
        root.setBorder(new EmptyBorder(24, 28, 24, 28));
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Seasonal Calendar");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(ink);

        JLabel subtitle = new JLabel("Kalender dan informasi pergantian musim");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(muted);

        JPanel titleStack = new JPanel();
        titleStack.setOpaque(false);
        titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));
        titleStack.add(title);
        titleStack.add(Box.createVerticalStrut(5));
        titleStack.add(subtitle);

        JPanel selectors = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        selectors.setOpaque(false);
        styleCombo(modeCombo);
        styleCombo(locationCombo);
        styleCombo(hemisphereCombo);
        selectors.add(modeCombo);
        selectors.add(locationCombo);
        selectors.add(hemisphereCombo);

        header.add(titleStack, BorderLayout.WEST);
        header.add(selectors, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(18, 0));
        center.setOpaque(false);

        JPanel calendarCard = new JPanel(new BorderLayout(12, 14));
        calendarCard.setBackground(panel);
        calendarCard.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel monthHeader = new JPanel(new BorderLayout());
        monthHeader.setOpaque(false);
        previousMonthButton.setToolTipText("Bulan sebelumnya");
        nextMonthButton.setToolTipText("Bulan berikutnya");
        previousMonthButton.addActionListener(e -> changeMonth(-1));
        nextMonthButton.addActionListener(e -> changeMonth(1));
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 19));
        monthLabel.setForeground(ink);
        monthHeader.add(previousMonthButton, BorderLayout.WEST);
        monthHeader.add(monthLabel, BorderLayout.CENTER);
        monthHeader.add(nextMonthButton, BorderLayout.EAST);
        calendarCard.add(monthHeader, BorderLayout.NORTH);

        JPanel calendarBody = new JPanel(new BorderLayout(0, 10));
        calendarBody.setOpaque(false);
        JPanel weekdayPanel = new JPanel(new GridLayout(1, 7, 7, 0));
        weekdayPanel.setOpaque(false);
        String[] weekdays = {"Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"};
        for (String weekday : weekdays) {
            JLabel label = new JLabel(weekday, SwingConstants.CENTER);
            label.setForeground(muted);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            weekdayPanel.add(label);
        }
        daysPanel.setOpaque(false);
        calendarBody.add(weekdayPanel, BorderLayout.NORTH);
        calendarBody.add(daysPanel, BorderLayout.CENTER);
        calendarCard.add(calendarBody, BorderLayout.CENTER);
        center.add(calendarCard, BorderLayout.CENTER);

        infoCard.setPreferredSize(new Dimension(290, 0));
        infoCard.setBackground(new Color(250, 251, 253));
        infoCard.setBorder(new EmptyBorder(20, 18, 18, 18));
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));

        JLabel infoHeading = new JLabel("Informasi Musim");
        infoHeading.setFont(new Font("SansSerif", Font.BOLD, 18));
        infoHeading.setForeground(ink);
        infoHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        selectedDateLabel.setForeground(muted);
        selectedDateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        selectedDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        configureWrappedArea(seasonLabel, new Font("SansSerif", Font.BOLD, 22), accent, 2);
        configureWrappedArea(periodLabel, new Font("SansSerif", Font.BOLD, 13), ink, 2);
        configureWrappedArea(descriptionArea, new Font("SansSerif", Font.PLAIN, 13), new Color(75, 85, 100), 7);
        configureWrappedArea(sourceLabel, new Font("SansSerif", Font.PLAIN, 11), muted, 2);

        sourceButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceButton.setFocusPainted(false);
        sourceButton.setEnabled(false);
        sourceButton.addActionListener(e -> openSource());

        infoCard.add(infoHeading);
        infoCard.add(Box.createVerticalStrut(20));
        infoCard.add(selectedDateLabel);
        infoCard.add(Box.createVerticalStrut(12));
        infoCard.add(seasonLabel);
        infoCard.add(Box.createVerticalStrut(12));
        infoCard.add(periodLabel);
        infoCard.add(Box.createVerticalStrut(12));
        infoCard.add(descriptionArea);
        infoCard.add(Box.createVerticalStrut(18));
        infoCard.add(sourceLabel);
        infoCard.add(Box.createVerticalStrut(12));
        infoCard.add(sourceButton);
        infoCard.add(Box.createVerticalGlue());

        center.add(infoCard, BorderLayout.EAST);
        root.add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("Data musim Indonesia bersumber dari BMKG. Periode prakiraan bukan prediksi hujan harian.");
        footer.setForeground(muted);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        root.add(footer, BorderLayout.SOUTH);
    }

    private void attachEvents() {
        modeCombo.addActionListener(e -> {
            boolean indonesiaMode = modeCombo.getSelectedIndex() == 0;
            locationCombo.setVisible(indonesiaMode);
            hemisphereCombo.setVisible(!indonesiaMode);
            refreshCalendar();
        });
        locationCombo.addActionListener(e -> refreshCalendar());
        hemisphereCombo.addActionListener(e -> refreshCalendar());
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
        combo.setFocusable(false);
    }

    private JButton navButton(String symbol) {
        JButton button = new JButton(symbol);
        button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));
        button.setForeground(accent);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setPreferredSize(new Dimension(42, 36));
        button.setFocusable(false);
        return button;
    }

    private void changeMonth(int amount) {
        YearMonth targetMonth = displayedMonth.plusMonths(amount);
        if (targetMonth.isBefore(MIN_MONTH) || targetMonth.isAfter(MAX_MONTH)) {
            return;
        }

        displayedMonth = targetMonth;
        int day = Math.min(selectedDate.getDayOfMonth(), displayedMonth.lengthOfMonth());
        selectedDate = displayedMonth.atDay(day);
        refreshCalendar();
    }

    private void refreshCalendar() {
        monthLabel.setText(displayedMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.forLanguageTag("id-ID"))
                + " " + displayedMonth.getYear());

        previousMonthButton.setEnabled(displayedMonth.isAfter(MIN_MONTH));
        nextMonthButton.setEnabled(displayedMonth.isBefore(MAX_MONTH));

        daysPanel.removeAll();
        LocalDate firstDay = displayedMonth.atDay(1);
        int offset = firstDay.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();

        for (int i = 0; i < offset; i++) {
            daysPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= displayedMonth.lengthOfMonth(); day++) {
            LocalDate date = displayedMonth.atDay(day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
            dayButton.setFocusPainted(false);
            dayButton.setBorderPainted(false);
            dayButton.setOpaque(true);
            dayButton.setBackground(date.equals(selectedDate) ? accent : Color.WHITE);
            dayButton.setForeground(date.equals(selectedDate) ? Color.WHITE : ink);
            if (date.equals(LocalDate.now())) {
                dayButton.setBorder(BorderFactory.createLineBorder(accent, 1));
            }
            dayButton.addActionListener(e -> {
                selectedDate = date;
                refreshCalendar();
            });
            daysPanel.add(dayButton);
        }

        while (daysPanel.getComponentCount() < 42) {
            daysPanel.add(new JLabel(""));
        }

        updateSeasonInformation();
        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void updateSeasonInformation() {
        selectedDateLabel.setText(selectedDate.getDayOfMonth() + " "
                + selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("id-ID"))
                + " " + selectedDate.getYear());

        boolean indonesiaMode = modeCombo.getSelectedIndex() == 0;
        locationCombo.setVisible(indonesiaMode);
        hemisphereCombo.setVisible(!indonesiaMode);

        if (!indonesiaMode) {
            String hemisphere = hemisphereCombo.getSelectedIndex() == 0 ? "NORTH" : "SOUTH";
            seasonLabel.setText(fourSeasonService.getSeason(selectedDate, hemisphere));
            periodLabel.setText("Pembagian meteorologis");
            descriptionArea.setText("Musim dihitung berdasarkan bulan kalender. Belahan bumi utara dan selatan memiliki pembagian musim yang berlawanan. Ini adalah pembagian meteorologis, bukan tanggal astronomis.");
            sourceLabel.setText("Sumber: aturan musim meteorologis");
            sourceButton.setEnabled(false);
        } else {
            String locationId = locationCombo.getSelectedIndex() == 0 ? "palembang" : "jakarta";
            SeasonForecast forecast = seasonDataService.findForecast(locationId, YearMonth.from(selectedDate));

            if (forecast == null) {
                String estimatedSeason = estimateIndonesianSeason(locationId, selectedDate.getMonthValue());
                seasonLabel.setText("Perkiraan " + estimatedSeason);
                periodLabel.setText("Estimasi berdasarkan pola bulanan");
                descriptionArea.setText("Data BMKG untuk bulan ini belum tersedia dalam dataset. "
                        + "Informasi musim yang ditampilkan merupakan estimasi umum berdasarkan pola musiman wilayah, "
                        + "bukan prakiraan resmi BMKG dan bukan prediksi cuaca harian.");
                sourceLabel.setText("Sumber: Estimasi kalender (bukan data BMKG)");
                sourceButton.setEnabled(false);
            } else {
                seasonLabel.setText(toSeasonName(forecast.getSeason()));
                periodLabel.setText("Awal musim menurut BMKG: " + forecast.getPeriodLabel());
                descriptionArea.setText(forecast.getSummary() + "\n\n" + forecast.getDetail());
                sourceLabel.setText("Sumber: " + forecast.getSourceName());
                sourceButton.setEnabled(true);
            }
        }

        updateWrappedRows();
    }

    private String toSeasonName(String season) {
        if ("HUJAN".equalsIgnoreCase(season)) return "Musim Hujan";
        if ("KEMARAU".equalsIgnoreCase(season)) return "Musim Kemarau";
        return season;
    }

    private String estimateIndonesianSeason(String locationId, int month) {
        if ("palembang".equals(locationId)) {
            if (month >= 10 || month <= 4) return "Musim Hujan";
            if (month >= 6 && month <= 9) return "Musim Kemarau";
            return "Peralihan";
        }
        if (month >= 11 || month <= 3) return "Musim Hujan";
        if (month >= 6 && month <= 9) return "Musim Kemarau";
        return "Peralihan";
    }

    private void configureWrappedArea(JTextArea area, Font font, Color foreground, int rows) {
        area.setFont(font);
        area.setForeground(foreground);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setOpaque(false);
        area.setFocusable(false);
        area.setColumns(22);
        area.setRows(rows);
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        area.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    private void updateWrappedRows() {
        int availableWidth = 254;
        seasonLabel.setRows(calculateWrappedLines(seasonLabel.getText(), seasonLabel.getFont(), availableWidth, 2));
        periodLabel.setRows(calculateWrappedLines(periodLabel.getText(), periodLabel.getFont(), availableWidth, 2));
        descriptionArea.setRows(calculateWrappedLines(descriptionArea.getText(), descriptionArea.getFont(), availableWidth, 7));
        sourceLabel.setRows(calculateWrappedLines(sourceLabel.getText(), sourceLabel.getFont(), availableWidth, 2));
        infoCard.revalidate();
        infoCard.repaint();
    }

    private int calculateWrappedLines(String text, Font font, int width, int minimum) {
        FontMetrics metrics = getFontMetrics(font);
        int lines = 0;
        for (String paragraph : text.split("\\n", -1)) {
            if (paragraph.isEmpty()) {
                lines++;
                continue;
            }
            int currentWidth = 0;
            int paragraphLines = 1;
            for (String word : paragraph.split("\\s+")) {
                int wordWidth = metrics.stringWidth(word + " ");
                if (currentWidth > 0 && currentWidth + wordWidth > width) {
                    paragraphLines++;
                    currentWidth = wordWidth;
                } else {
                    currentWidth += wordWidth;
                }
            }
            lines += paragraphLines;
        }
        return Math.max(minimum, lines);
    }

    private void openSource() {
        String locationId = locationCombo.getSelectedIndex() == 0 ? "palembang" : "jakarta";
        SeasonForecast forecast = seasonDataService.findForecast(locationId, YearMonth.from(selectedDate));
        if (forecast == null || forecast.getSourceUrl() == null || forecast.getSourceUrl().isBlank()) {
            JOptionPane.showMessageDialog(this, "Tautan sumber tidak tersedia.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            if (!Desktop.isDesktopSupported()) {
                throw new UnsupportedOperationException("Desktop tidak didukung pada sistem ini.");
            }
            Desktop.getDesktop().browse(URI.create(forecast.getSourceUrl()));
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Tidak dapat membuka tautan sumber: " + exception.getMessage(),
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static YearMonth clampMonth(YearMonth month) {
        if (month.isBefore(MIN_MONTH)) return MIN_MONTH;
        if (month.isAfter(MAX_MONTH)) return MAX_MONTH;
        return month;
    }

    private static LocalDate clampDate(LocalDate date) {
        LocalDate min = MIN_MONTH.atDay(1);
        LocalDate max = MAX_MONTH.atEndOfMonth();
        if (date.isBefore(min)) return min;
        if (date.isAfter(max)) return max;
        return date;
    }
}
