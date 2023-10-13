import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VipController {
	@FXML
	private PieChart piechart;
	
	public void pie() throws SQLException {
		PostModel postModel=new PostModel();
		ResultSet result=postModel.piechartforvip();
		if (result.next()) {
			int shareCount0To99 = result.getInt("range_0_to_99");
			int shareCount100To999 = result.getInt("range_100_to_999");
			int shareCount1000AndAbove = result.getInt("range_1000_and_above");

			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
					new PieChart.Data("0-99", shareCount0To99), 
					new PieChart.Data("100-999", shareCount100To999),
					new PieChart.Data("1000+", shareCount1000AndAbove));
			
			piechart.getData().addAll(pieChartData)	;		
		}
	}
}
