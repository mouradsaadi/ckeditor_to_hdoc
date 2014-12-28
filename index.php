<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HDOC Converter</title>
        <!-- Make sure the path to CKEditor is correct. -->
        <script src="ckeditor/ckeditor.js" type="text/javascript" charset="UTF-8"></script>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
		<!-- Latest compiled and minified JavaScript -->
		<!--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>-->
    </head>
    <body>
        <form>
            <textarea name="editor1" id="editor1" rows="10" cols="80">
               Tape text her
            </textarea>
			<div class="btn-group btn-group-justified" role="group" aria-label="...">
				<section>
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-primary btn-lg" onclick="return getOutput();">Convert to HDOC/Opale</button>
					</div>
				<section>
			</div>
            <script>
                // Replace the <textarea id="editor1"> with a CKEditor
                // instance, using default configuration.
					CKEDITOR.config.entities = false;
					CKEDITOR.config.entities_latin = false;
					CKEDITOR.config.basicEntities =false;
					CKEDITOR.config.entities_greek = false;
					CKEDITOR.config.fillEmptyBlocks = false;
					CKEDITOR.replace( 'editor1');

					
					function getOutput() {
						getData = CKEDITOR.instances.editor1.getData();

					  getRequest(
					      'convert.php', // URL for the PHP file
					       drawOutput,  // handle successful request
					       drawError,    // handle error
					       getData     //ckeditor content
					  );
					  return false;
					}  
					// handles drawing an error message
					function drawError() {
					    var container = document.getElementById('output');
					    container.innerHTML = 'Bummer: there was an error!';
					}
					// handles the response, adds the html
					function drawOutput(responseText) {
					    var container = document.getElementById('output');
					    container.innerHTML = responseText;
					}
					// helper function for cross-browser request object
					function getRequest(url, success, error,data) {
					    var req = false;
					    try{
					        // most browsers
					        req = new XMLHttpRequest();
					    } catch (e){
					        // IE
					        try{
					            req = new ActiveXObject("Msxml2.XMLHTTP");
					        } catch(e) {
					            // try an older version
					            try{
					                req = new ActiveXObject("Microsoft.XMLHTTP");
					            } catch(e) {
					                return false;
					            }
					        }
					    }
					    if (!req) return false;
					    if (typeof success != 'function') success = function () {};
					    if (typeof error!= 'function') error = function () {};
					    req.onreadystatechange = function(){
					        if(req.readyState == 4) {
					            return req.status === 200 ? 
					                success(req.responseText) : error(req.status);
					        }
					    }
					    req.open("POST", url, true);
					    var params = "data="+getData;
					    console.log(params);
					    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					    req.send(params);
					    return req;
					}
					
            </script>	
        </form>
        <div id="output"class="alert alert-info" role="alert"></div>
    </body>
</html>