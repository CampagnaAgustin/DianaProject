$(document).ready(function() {
	
	$("#upload_widget_opener").click(function () {
		cloudinary.openUploadWidget({
	
		upload_preset : 'qsfge8wv', 
		cloud_name: 'dysm2mo4e',
		theme: 'white',
		multiple: true,
		max_image_width: 750,
		max_image_height: 750,
		max_files: 5,
		folder: 'mi_carpeta',
		sources: [ 'local', 'url', 'facebook'], 
	}, function(error, result) {
		console.log(error, result)
		var url = result[0].secure_url;
        
        console.log (url);
        
        $("form").find("#foto").val( url );
	})
})

})