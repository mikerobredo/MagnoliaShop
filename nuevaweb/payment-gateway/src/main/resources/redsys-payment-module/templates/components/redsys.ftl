<div class="row">
    <div class="col-md-5">
        <div class="column">
            <label class="cardinfo-label" for="card-number">Numero de tarjeta</label>
            <div class='input-wrapper' id="card-number"></div>
        </div>
        <div class="column">
            <div class="cardinfo-exp-date">
                <label class="cardinfo-label" for="expiration-date">Mes Caducidad (MM)</label>
                <div class='input-wrapper' id="expiration-month"></div>
            </div>
            <div class="cardinfo-exp-date2">
                <label class="cardinfo-label" for="expiration-date2">Año Caducidad (AA)</label>
                <div class='input-wrapper' id="expiration-year"></div>
            </div>
        </div>
        <div class="cardinfo-cvv">
            <label class="cardinfo-label" for="cvv">CVV</label>
            <div class='input-wrapper' id="cvv"></div>
        </div>
        <div class="cardinfo-getPayButton">
            <label class="cardinfo-label" for="boton">boton</label>
            <div class='input-wrapper' id="boton"></div>
        </div>
    </div>
</div>
<input type="hidden" id="token"></input>
<!-- Listener de recepción de ID de operación -->
<script>
    window.addEventListener("message", function receiveMessage(event) {
        storeIdOper(event, "token");
    });
</script>
<script>
    getCardInput('card-number', 'input-group mb-3');
    getExpirationMonthInput('expiration-month', 'input-group mb-3');
    getExpirationYearInput('expiration-year', "input-group-prepend");
    getCVVInput('cvv', "input-group-prepend");
    getPayButton('boton', "btn btn-primary", 'Pagar con Redsys', '999008881', '1', '523358461609');
</script>