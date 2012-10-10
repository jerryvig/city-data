importClass(java.net.URL);
importClass(java.io.BufferedReader);
importClass(java.io.InputStreamReader);

var cityList = [ 'Denver','Boston','Chicago','Miami','Philadelphia' ];
for ( var i=0; i<cityList.length; i++ ) {
	processCity( cityList[i] );
	print( cityList[i] );
}

function processCity( cityName ) {
	var url = new URL("http://en.wikipedia.org/wiki/"+cityName);
	var reader = new BufferedReader(new InputStreamReader(url.openStream()));
	
	var line = '';
	while ( (line = reader.readLine()) != null ) {
		print( line );
	}
	
	reader.close();
}

