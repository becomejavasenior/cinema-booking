/**
 * Created by Andrey on 2/19/2017.
 */

$(document).ready(function () {
    window.count= -1;
    var table = $('#movies').DataTable({
        "bLengthChange": false,
        "bAutoWidth": false,
        "bFilter": false,
        "ajax" : {
            "url": "/getAllFilms",
            "type": "POST"
        },
        serverSide:true,
        columns: [
            { "data": null, "title": "", "visible": false, "render": function(data){
                window.count++;
                return drawSection(data, count);
            }
            }
        ]
    });

});

$(document).ready( function () {
    $('#movies').on('page.dt', function() {
        $("div.row.my-content-row").remove();
        window.count=-1;
    });
} );


function drawSection(response, counter){

    let rowdiv = $('<div class="row my-content-row"></div>');

    $('.container').find(".my-header").after(rowdiv);

    let $lastRow = getLastRow();

    let newRow;

    if ((counter % 3) === 0) {

        $('.container').find('.my-content-row').last().after(rowdiv);

        $lastRow = rowdiv;

    }

    let $movieItem = response;

    let $curItem = getCloneItem();

    $curItem.find('h3').find('a').attr('href', '/seans/' + $movieItem.id).empty().append('<h4>' + $movieItem.name + '</h4>');

    $curItem.show();

    $lastRow.append($curItem);

    getLastRow().val($lastRow);

    return "ok";

}

function getLastRow() {
    return $('.container').find('.my-content-row').last();
}

function getCloneItem() {
    return $('.portfolio-item').last().clone();
}