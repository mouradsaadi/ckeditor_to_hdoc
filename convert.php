<?php
	//echo mb_convert_encoding($_POST['data'],"UTF-8","auto");
	
	
	//$output = preg_replace_callback("/(&#[0-9]+;)/", function($m) { return mb_convert_encoding($m[1], "UTF-8", "HTML-ENTITIES"); }, ); 
	//echo $output;
 
	//echo "data: ".$data."\n\n";
	$handle = fopen("CkInput.html", "w");
	fwrite($handle, $_POST['data']);
	
	$cmd = 'ant -buildfile ckeditor_to_hdoc.ant';
	$out_cmd = shell_exec($cmd);
	echo "<div class=\"jumbotron\"> <h2> <strong>Convert to HDOC:</strong> $cmd \n\n";
	echo "<pre> $out_cmd </pre>";
	echo "<p><a class=\"btn btn-primary btn-lg\" href=\"outZip.hdoc\" role=\"button\">Download Hdoc File</a></p></div>";
	
	
	//echo "<strong>ant script:</strong> $cmd \n\n";
	//echo $out_cmd;
	//echo "<h3>Download HDOC zip: <a href='outZip.hdoc'>output.hdoc</a><h3>";
	
	$cmd = 'ant -buildfile ./hdoctoopale/hdoc_to_opale.ant -DInputPath ../outZip.hdoc -Dtmpdir ../opalescar/';
	$out_cmd = shell_exec($cmd);

	//echo "<strong>ant script:</strong> $cmd \n\n";
	//echo $out_cmd;
	//echo "<h3>Download Scar zip: <a href='\hdoctoopale\out.scar'>out.scar</a><h3>";
	echo "<div class=\"jumbotron\"> <h2> <strong>Convert to Opale:</strong> $cmd \n\n";
	echo "<pre> $out_cmd </pre>";
	echo "<p><a class=\"btn btn-primary btn-lg\" href=\"hdoctoopale\out.scar\" role=\"button\">Download Scar File</a></p></div>";
?>