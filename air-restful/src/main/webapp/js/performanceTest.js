function buildTable() {
	dojo.xhrGet({
		url : "performanceXmlv1.2.json",
		preventCache : false,
		load : function(data, ioArgs) {
			var performancePropertys = data.performancePropertys;
			var keys = new Array();
			if (performancePropertys != null) {
				var json_data = "[";
				for ( var i = 0; i < performancePropertys.length; i++) {
					var datablocks = performancePropertys[i].datablocks;
					for ( var j = 0; j < datablocks.length; j++) {
						var datas = datablocks[j].performanceDatas;
						var row_data = "{";
						for ( var k = 0; k < datas.length; k++) {
							var key = datas[k].column;
							var value = datas[k].value;
							if (k < datas.length - 1)
								row_data += "'" + key + "':'"
										+ value + "',";
							else
								row_data += "'" + key + "':'"
										+ value + "'";
							keys[k] = key;
						}

						if (j == datablocks.length - 1
								&& i == performancePropertys.length)
							row_data += "}";
						else
							row_data += "},";

						json_data += row_data;
					}
				}
				json_data += "]";
				console.log(json_data);

				var data_list = eval(json_data);

				/*set up data store*/
				var data = {
					identifier : 'id',
					items : []
				};

				var rows = 60;
				if (rows > data_list.length)
					rows = data_list.length;
				for ( var n = 0, l = data_list.length; n < rows; n++) {
					data.items.push(dojo.mixin({
						id : n + 1
					}, data_list[n % l]));
				}

				var store = new dojo.data.ItemFileWriteStore({
					data : data
				});

				var key_data = "[[";
				for ( var m = 0; m < keys.length; m++) {
					var key = keys[m];
					if (m < keys.length - 1)
						key_data += "{'name': '" + key
								+ "', 'field': '" + key + "'},";
					else
						key_data += "{'name': '" + key
								+ "', 'field': '" + key
								+ "', 'width': '120px'}";
				}
				key_data += "]]";
				var layout = eval(key_data);

				/*create a new grid:*/
				var grid = new dojox.grid.DataGrid({
					id : 'grid',
					store : store,
					structure : layout,
					rowSelector : '20px'
				}, document.createElement('div'));
				dojo.byId("tableContainer").appendChild(grid.domNode);
				grid.startup();
			}
		},
		headers : {
			"Accept" : "application/json"
		},
		handleAs : 'json'
	});
}

function buildChart(){
	var chart1 = new dojox.charting.Chart("chartContainer").
	addAxis("x", {
		labels: [{value: 1, text: "Jan"}, {value: 2, text: "Feb"},
		         {value: 3, text: "Mar"}, {value: 4, text: "Apr"},
		         {value: 5, text: "May"}, {value: 6, text: "Jun"},
		         {value: 7, text: "Jul"}, {value: 8, text: "Aug"},
		         {value: 9, text: "Sep"}, {value: 10, text: "Oct"},
		         {value: 11, text: "Nov"}, {value: 12, text: "Dec"}],
		natural: true,
		fixed: true});
	chart1.addSeries("Series A", [1, 1.2, 3.2, 1.4, 2.5,2, 2.2, 1.2, 3.4, 0.5]);
	chart1.render();
}