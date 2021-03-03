/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function showPreview(imageInput) {
    const imgPreview = document.getElementById("img-preview");
    const files = imageInput.files[0];
    if (files) {
        const fileReader = new FileReader();
        fileReader.readAsDataURL(files);
        fileReader.addEventListener("load", function () {
            imgPreview.style.display = "block";
            imgPreview.innerHTML = '<img class="img-thumbnail img-fluid" style="width: 200px; height: 200px; object-fit: cover;" src="' + this.result + '" />';
        });
    } else {
        imgPreview.innerHTML = "";
    }
}