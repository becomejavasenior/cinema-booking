/**
 * Created by Andrey on 2/19/2017.
 */
$(document).ready(function () {
    var count= -1;
    var table = $('#movies').DataTable({
        "bLengthChange": false,
        "bAutoWidth": false,
        "bFilter": false,
        "ajax" : {
            "url": "/test/movies",
            "type": "POST"
        },
        serverSide:true,
        columns: [
            { "data": null, "title": "", "visible": false, "render": function(data){
                count++;
                //console.log("count: "+count);
                return drawSection(data, count);
            }
            }
        ]
    });
});

function drawSection(response, counter){

    console.log("drawSection running "+response.name);

    let rowdiv = $('<div class="row" id="myrow"></div>');

        let $lastRow = getLastRow();

        let newRow;

        if ((counter % 3) === 0){

            $('.container').find('#upperrow').last().after(rowdiv);

            $lastRow = rowdiv;

        }

        let $movieItem = response;

        let $curItem = getCloneItem();

        $curItem.find('h3').find('a').attr('href', '/seans/'+$movieItem.id).empty().append('<h4>'+$movieItem.name+'</h4>');

        $curItem.show();

        $lastRow.append($curItem);

        getLastRow().val($lastRow);

    return "ok";

}

function getLastRow() {
    return $('.container').find('#myrow').last();
}

function getCloneItem() {
    return $('.portfolio-item').last().clone();
}