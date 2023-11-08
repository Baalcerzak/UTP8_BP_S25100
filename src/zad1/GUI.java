package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.List;

public class GUI extends JFrame {


    public GUI(List<Offer> offers){
        String[][] data = {

        };
        String[] columnNames={"loc", "country", "depDate", "arrDate", "place", "price", "currencyCode"};
        JPanel panel = new JPanel();
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        TableRowSorter sorter = new TableRowSorter(model);
        JTable table = new JTable(model);
        table.setRowSorter(sorter);
        panel.add(new JScrollPane(table));


        String[] filters = { "pl", "en" };
        String[] countries = { "United States", "Italy", "Japan" };
        JComboBox filtersCombo = new JComboBox(filters);
        JComboBox country  = new JComboBox(countries);
        JButton button = new JButton("Rest filters");

        addOffersToTable(offers, model);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRowFilter(null);
            }
        });
        filtersCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                RowFilter<DefaultTableModel, Object> rf = null;
                try {
                    if (e.getSource() == filtersCombo) {
                        rf = RowFilter.regexFilter(filtersCombo.getSelectedItem().toString(), 0);
                    }

                } catch (java.util.regex.PatternSyntaxException e1) {
                    return;
                }
                sorter.setRowFilter(rf);
            }
        });
        country.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                RowFilter<DefaultTableModel, Object> rf = null;
                try {
                    if (e.getSource() == country) {
                        rf = RowFilter.regexFilter(country.getSelectedItem().toString(), 1);
                    }

                } catch (java.util.regex.PatternSyntaxException e1) {
                    return;
                }
                sorter.setRowFilter(rf);
            }
        });

        panel.add(filtersCombo);
        panel.add(country);
        panel.add(button);
        add(panel);
        setSize(600,600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void addOffersToTable(List<Offer> offers, DefaultTableModel model){
        Object rowData[] = new Object[7];
        for(int i = 0; i < offers.size(); i++)
        {
            rowData[0] = offers.get(i).loc;
            rowData[1] = offers.get(i).country;
            rowData[2] = offers.get(i).depDate;
            rowData[3] = offers.get(i).arrDate;
            rowData[4] = offers.get(i).place;
            rowData[5] = offers.get(i).price;
            rowData[6] = offers.get(i).currencyCode;
            model.addRow(rowData);
        }
    }
}
