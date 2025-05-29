import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShoeStore extends JFrame {

    private JLabel largeImage, productName, productPrice, productBrand, productDetails;

    public ShoeStore() {
        setTitle("Shoe Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        largeImage = createLabel("", 0, 0, null);
        largeImage.setPreferredSize(new Dimension(270, 270));

        productName = createLabel("", Font.BOLD, 24, Color.BLACK);
        productName.setPreferredSize(new Dimension(270, 30));
        productPrice = createLabel("", Font.BOLD, 24, Color.BLACK);
        productPrice.setPreferredSize(new Dimension(270, 30));
        productBrand = createLabel("", Font.PLAIN, 18, Color.BLACK);
        productBrand.setPreferredSize(new Dimension(270, 30));
        productDetails = createLabel("", Font.PLAIN, 16, Color.GRAY);
        productDetails.setPreferredSize(new Dimension(270, 100));

        leftPanel.add(largeImage);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(productName);
        leftPanel.add(productPrice);
        leftPanel.add(productBrand);
        leftPanel.add(productDetails);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[][] products = {
            {"Lab3/img1.png", "4DFWD PULSE SHOES", "Adidas", "$160.00", "This product is excluded from all promotional discounts and offers."},
            {"Lab3/img2.png", "FORUM MID SHOES", "Adidas", "$100.00", "This product is excluded from all promotional discounts and offers."},
            {"Lab3/img3.png", "SUPERNOVA SHOES", "Adidas", "$150.00", "NMD City Stock 2"},
            {"Lab3/img4.png", "NMD City Stock 2", "Adidas", "$160.00", "NMD City Stock 2"},
            {"Lab3/img5.png", "4DFWD PULSE SHOES", "Adidas", "$120.00", "This product is excluded from all promotional discounts and offers."},
            {"Lab3/img6.png", "4DFWD PULSE SHOES", "Adidas", "$160.00", "This product is excluded from all promotional discounts and offers."},
            {"Lab3/img1.png", "4DFWD PULSE SHOES", "Adidas", "$160.00", "This product is excluded from all promotional discounts and offers."},
            {"Lab3/img2.png", "FORUM MID SHOES", "Adidas", "$100.00", "This product is excluded from all promotional discounts and offers."}
        };

        for (String[] product : products) {
            addProduct(rightPanel, product[0], product[1], product[2], product[3], product[4]);
        }

        return rightPanel;
    }

    // Hàm tiện ích để cắt ngắn chuỗi và thêm "..."
    private String truncateText(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    private void addProduct(JPanel panel, String imagePath, String name, String brand, String price, String detail) {
        RoundedPanel productPanel = new RoundedPanel(50);
        productPanel.setLayout(new BorderLayout());
        productPanel.setBackground(new Color(220, 220, 220));
        productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Cắt ngắn tên và mô tả nếu quá dài
        String truncatedName = truncateText(name, 16); // Giới hạn 15 ký tự cho tên
        String truncatedDetail = truncateText(detail, 33); // Giới hạn 25 ký tự cho mô tả

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(createLabel(truncatedName, Font.BOLD, 16, Color.BLACK), BorderLayout.NORTH);
        topPanel.add(createLabel(truncatedDetail, Font.PLAIN, 12, Color.GRAY), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(createLabel(brand, Font.PLAIN, 14, Color.BLACK), BorderLayout.WEST);
        bottomPanel.add(createLabel(price, Font.BOLD, 16, Color.BLACK), BorderLayout.EAST);

        productPanel.add(topPanel, BorderLayout.NORTH);
        productPanel.add(createImagePanel(imagePath), BorderLayout.CENTER);
        productPanel.add(bottomPanel, BorderLayout.SOUTH);

        productPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateLeftPanel(imagePath, name, brand, price, detail);
                resetProductPanels(panel, productPanel);
            }
        });

        panel.add(productPanel);
    }

    private JLabel createImagePanel(String imagePath) {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        return imageLabel;
    }

    private void updateLeftPanel(String imagePath, String name, String brand, String price, String detail) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        largeImage.setIcon(new ImageIcon(scaledImage));

        productName.setText("<html>" + name + "</html>");
        productPrice.setText("<html>" + price + "</html>");
        productBrand.setText("<html>" + brand + "</html>");
        productDetails.setText("<html>" + detail + "</html>");

        largeImage.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    }

    private void resetProductPanels(JPanel panel, JPanel clickedPanel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof RoundedPanel) {
                RoundedPanel p = (RoundedPanel) component;
                if (p == clickedPanel) {
                    p.setBackground(new Color(173, 216, 230));
                } else {
                    p.setBackground(new Color(220,220,220));
                }
            }
        }
    }

    private JLabel createLabel(String text, int style, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(color);
        return label;
    }

    public class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShoeStore::new);
    }
}