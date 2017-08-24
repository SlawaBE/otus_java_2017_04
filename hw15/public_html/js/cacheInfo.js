function refresh() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
            var cacheInfo = JSON.parse(this.response);
            document.getElementById("cacheSize").innerHTML = cacheInfo.cacheSize
            document.getElementById("cacheHit").innerHTML = cacheInfo.cacheHit
            document.getElementById("cacheMiss").innerHTML = cacheInfo.cacheMiss
        }
    };

    xhttp.open("GET", "/cacheInfo", true);
    xhttp.send();
}