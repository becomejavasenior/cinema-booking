(function (){


    function getSession(){
        // let session = {};

        $.ajax({
            url: '/getAllFilms',
            method: 'GET',
            success: function (response) {
                // session.price = response.price;
                // session.filmId = filmId;
                // session.filmName = response.filmName;
                // session.filmDate = response.filmDate;
                // session.map = response.map;

                return drowSection(response);
            }
        });
    }


    function drowSection(response){

        let rowdiv = $('<div class="row" id="myrow"></div>');

        let counter = 0;

        for (let i = 0 ; i < response.length ; i++ ){

            let $lastRow = getLastRow();  //поиск последней строки

            // let amountOfCol = $lastRow.children('.col').length; //колличесво

            let newRow;

            // let modAmount = amountOfCol % 3;
            // (modAmount % 3 === 0) && (amountOfCol !== 0)
            if ((counter % 3) === 0){

                // newRow = $lastRow.clone();
                //
                // newRow.deleteCell(0);

                $('.container').find('#upperrow').last().after(rowdiv);

                $lastRow = rowdiv;

            }

            let $movieItem = response[i];

            let $curItem = getCloneItem();

            $curItem.find('h3').find('a').attr('href', '/seans/'+$movieItem.id).empty().append('<h4>'+$movieItem.name+'</h4>');

            $curItem.show();

            $lastRow.append($curItem);

            getLastRow().val($lastRow);

            counter++;
        }

        return "ok";

    }

    function getLastRow() {
        return $('.container').find('#myrow').last();
    }

    function getCloneItem() {
        return $('.portfolio-item').last().clone();
    }

    getSession();

    // $(document).change(function(){
    //
    //     // let containerDiv = document.getElementsByClassName('container');
    //
    //     // document.getElementsByTagName('body')[0].appendChild(div);
    //
    //     let rowId = 1;
    //
    //     let filmId = '';
    //     let hreff = '#';
    //
    //     let div =   '<div class="row" id="rowId"></div>';
    //     let inputDiv =
    //         '<div class="col-md-4 portfolio-item">' +
    //             '<a href="">' +
    //                 '<img class="img-responsive" src="http://placehold.it/700x400" alt=""/>' +
    //             '</a>' +
    //             '<h3>' +
    //                 '<a href="" th:href="" id="filmId" onclick="clickFilm(id)">Фильм 1</a>' +
    //             '</h3>' +
    //             '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam viverra euismod odio, gravida pellentesque urna varius vitae.</p>' +
    //         '</div>' ;
    //
    //     //noinspection JSValidateTypes
    //     div.getElementById('rowId').id =rowId;
    //
    //     document.getElementsByClassName('container').appendChild(div);
    //
    //     for(let i = 1; i = session.size(); i++ ){
    //
    //         var idMovie = session[i].id;
    //
    //         inputDiv.getElementById('filmId').id = idMovie;
    //
    //         inputDiv.getElementById(idMovie).href = "/getSchedule/"+idMovie;
    //
    //         $("#"+i).html(inputDiv);
    //
    //         if ((i % 3) === 0){
    //             rowId++;
    //
    //             document.getElementsByClassName('container').appendChild(div);
    //
    //             //noinspection JSValidateTypes
    //             div.getElementById('rowId').id =rowId;
    //         }
    //
    //     }
    //
    // });

})();
