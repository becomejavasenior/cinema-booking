(function (){


    deleteAllContent();
    getSession();

    function getSession(){
    // let session = {};

        $.ajax({
            url: '/getAllFilms',
            method: 'GET',
            success: function (response) {

                let status = drowSection(response);
                console.log(status)
                return status;
            }
        });
    }

    function deleteAllContent() {
        $('.row .my-content-row').remove();
    }

    function drowSection(response){

        let rowdiv = $('<div class="row my-content-row"></div>');

        $('.container').find(".my-header").after(rowdiv);
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

                $('.container').find('.my-content-row').last().after(rowdiv);

                $lastRow = rowdiv;

            }

            let $movieItem = response[i];

            let $curItem = getCloneItem();

            $curItem.find('h3').find('a').attr('href', '/seans/'+$movieItem.id).empty().append('<h4>'+$movieItem.name+'</h4>');
            $curItem.find('.poster-link').attr('href', '/seans/'+$movieItem.id)

            $curItem.show();

            $lastRow.append($curItem);

            getLastRow().val($lastRow);

            counter++;
        }

        // Here we draw random movies images
        window.drawRandom.draw();

        return "ok";

    }

    function getLastRow() {
        return $('.container').find('.my-content-row').last();
    }

    function getCloneItem() {
        return $('.portfolio-item').last().clone();
    }

})();





















