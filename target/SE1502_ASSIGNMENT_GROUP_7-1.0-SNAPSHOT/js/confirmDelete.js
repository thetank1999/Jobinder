/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function confirmDelete(form) {
    if (confirm("Are you sure you want to delete?")) {
        form.submit();
    }
}