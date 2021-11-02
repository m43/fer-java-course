$(document).ready( 
    function(){
        // getAllThumbnails();
        getAllTags();
    }
);

function getAllTags(){
    $.ajax(
        {
            url: "rest/images/tags",
            dataType: "json",
            success: function(data) {
                let tagsHTML = "";

                let colors = ["#f1c40f","#f39c12","#e67e22","#d35400"];
                // let colors3 = ["#2ecc71","#1abc9c","#16a085","#27ae60","#3498db","#2980b9"];
                // let colors2 = ["#5A3F37", "#2C7744", "#001f3f", "#011", "#55246b", "#3D9970", "#FFDC00", "#FF851B", "#F0A830", "#FF3D0D", "#588C7E", "#F2E394", "#F2AE72", "#D96459", "#8C4646", "#27C2C4", "#262E5A", "#852026", "#CDE472", "#6A5ACD", "#473C8B", "#222"];

                tagsHTML += '<button class="tag" style="background-color: #2ecc71;" onclick="getAllThumbnails()"><span>ALL</span></button>\n'                
                for(let i = 0; i < data.length; i++){
                    tagsHTML += '<button class="tag" style="background-color: '
                    + colors[Math.floor(Math.random() * colors.length)]
                    + ';" onclick="onTagClick(this)" ><span>'
                    + data[i] + '</span></button>' + '\n'
                }

                $("#tags").html(tagsHTML);
            }
        }
    );
}

function getAllThumbnails(){
    $.ajax(
        {
            url: "rest/images",
            dataType: "json",
            success: loadThumbnailsFromJSON
        }
    );
}

function getThumbnailsWithTag(tag) {
    $.ajax(
        {
            url: "rest/images/tag/"+tag,
            dataType: "json",
            success: loadThumbnailsFromJSON
        }
    );
}    


function loadThumbnailsFromJSON(x) {
    galleryThumbnails = "";
    for(let i = 0; i<x.length; i++){
        let image = x[i];

        imageText = 
            '<div class="image"><img onclick="onThumbnailClick(this)" id="'
            +image.id+'" src="image?id='+image.id+'&dimensions=150" alt="'
            +image.description+'" width="150" height="150"><div class="desc">'
            +image.description+'</div></div>';

        galleryThumbnails += imageText + "\n";
    }
    $("#gallery").html(galleryThumbnails);
}            

function onThumbnailClick(element){
    let id = element.id;
    
    $("#gallery-display").hide();
    
    $.ajax(
        {
            url: "rest/images/id/"+id,
            dataType: "json",
            success: function(x){
            	
            	$("#gallery-display").html("");
            	
                let desc = document.createElement('h1');
                let span = document.createElement('span');
                desc.append(span);
                desc.className = 'desc';
                span.innerHTML = x.description;
            	span.className = 'supersize montserrat bold';
            	
                
            	let img = new Image();
            	img.onload = function() { $("#gallery-display").show(); }
            	img.src = 'image?id='+id;
            	
            	let tags = document.createElement('div');
            	tags.className = "tags";
            	
            	let colors = ["#f1c40f","#f39c12","#e67e22","#d35400"];                
            	tagsHTML = "";
            	for(let i = 0; i < x.tags.length; i++){
                    tagsHTML += '<button class="tag" style="background-color: '
                    + colors[Math.floor(Math.random() * colors.length)]
                    + ';" onclick="onTagClick(this)" ><span>'
                    + x.tags[i] + '</span></button>' + '\n'
                }
                tags.innerHTML = tagsHTML;
            	
                
                $("#gallery-display").append(desc);
                $("#gallery-display").append(img);
                $("#gallery-display").append(tags);                    
            }
        }
    );
        
}

function onTagClick(element) {
	getThumbnailsWithTag(element.textContent);
}