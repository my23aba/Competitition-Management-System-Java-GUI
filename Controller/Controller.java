package Controller;
import view.CompetitionGUI;
import model.CompetitorList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private CompetitionGUI view;
    private CompetitorList model;

    public Controller(CompetitionGUI view, CompetitorList model) {
        this.view = view;
        this.model = model;

        // Add action listeners
        view.getUpdateButton().addActionListener(new UpdateButtonListener());
        view.getEditButton().addActionListener(new EditButtonListener());
        view.getRemoveButton().addActionListener(new RemoveButtonListener());
        view.getReportButton().addActionListener(new ReportButtonListener());
        view.getShortButton().addActionListener(new ShortButtonListener());
        view.getAddCompetitorButton().addActionListener(new AddCompetitorButtonListener());
        view.getSearchButton().addActionListener(new SearchButtonListener());
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
         //   model.updateCompetitorTable();
            view.updateCompetitorTable();
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.editCompetitorDetails();
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.removeCompetitor();
        }
    }

    private class ReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.generateReport();
        }
    }

    private class ShortButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.shortReport();
        }
    }

    private class AddCompetitorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.addCompetitorFromForm();
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.searchCompetitor();
        }
    }
}
