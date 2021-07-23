/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function buildWorkDetailUSP(usp) {
    const locationId = document.getElementById("locationId").value;
    const languageIds = Array.from(document.querySelectorAll("input[type='checkbox'][name='languageId']:checked")).map(e => e.value);
    const fieldId = document.getElementById("fieldId").value;
    const levelId = document.getElementById("levelId").value;

    console.log(languageIds);

    if (locationId && parseInt(locationId) !== -1) {
        usp.append("locationId", locationId);
    }

    for (let languageId of languageIds) {
        usp.append("languageId", languageId);
    }

    if (fieldId && parseInt(fieldId) !== -1) {
        usp.append("fieldId", fieldId);
    }

    if (levelId && parseInt(levelId) !== 1) {
        usp.append("levelId", levelId);
    }
}