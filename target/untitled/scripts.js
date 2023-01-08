function validate(isGraph, x, y)
{
    const submit = document.getElementById('submit');
    var fl = true;
    fl &= validateR();
    fl &= (validateX(isGraph, x) && validateY(isGraph, y));
    submit.disabled = !fl;
    return fl;
}

function validateY(isGraph, y) {
    let input = document.getElementById('inpY');
    console.log(input.value);
    if (!isGraph) {
        y = try_parse(input.value);
        if (y[0] && -3 < y[1] && y[1] < 3) {
            input.className = "";
            return true;
        } else {
            input.className = "incorrect";
            return false;
        }
    }
    else{
        if (-3 < y && y < 3) {
            input.className = "";
            return true;
        } else {
            input.className = "incorrect";
            return false;
        }
    }
}

function validateX(isGraph, x) {
    let input = document.getElementById('inpX');
    console.log(input.value);

    var is_parsed = try_parse(input.value);
    if(!isGraph) {
        if(is_parsed[0] && -6 <= is_parsed[1] && is_parsed[1] <= 6){
            input.className = "";
            return true;
        }
        else{
            input.className = "incorrect";
            return false;
        }
    }
    else{
        if(-6 <= x && x <= 6){
            input.className = "";
            return true;
        }
        else{
            input.className = "incorrect";
            return false;
        }
    }

}

function validateR() {
    let input = document.getElementsByClassName('inpR');
    for (let i = 0; i < 5; i++) {
        //console.log(input[i]);
        if (input[i].checked){
            var R = try_parse(input[i].value);
            if (!R[0] || !(R[1] === 1 || R[1] === 2 || R[1] === 3 || R[1] === 4 || R[1] === 5)){
                input[i].className = "inpR incorrect";
                return false;
            }
            console.log("figure" + (i+1));
            let pict = document.getElementById("pict");
            console.log(pict.attributes);
            document.getElementById("pict")
                .contentDocument.getElementById("figure" + (i+1)).style.fillOpacity="0.7";
            input[i].className = "inpR";
        }
        else {
            console.log("figure" + (i+1));
            document.getElementById("pict")
                .contentDocument.getElementById("figure" + (i+1)).style.fillOpacity="0";
        }
    }
    return true;
}

function try_parse(x) {
    try {
        var a = parseFloat(x);
        const reg = new RegExp("^-?\\d*\\.?\\d*$");
        if (isNaN(a) || !reg.test(x))
            return [false];
        return [true, a];
    } catch (exc) {
        return [false];
    }
}

function errThrow(message){
    $('#err').html(message);
}

function loaded()
{
    validate(false, 0, 0);
    document.getElementById('graph').addEventListener("click", point_clicked, false);
}

function point_clicked(e)
{
    let element = document.getElementById('pict');
    let click_x = e.clientX, click_y = e.clientY;
    let pos = element.getBoundingClientRect();
    let screen_x = pos.x, screen_y = pos.y;
    let scale = (element.getBoundingClientRect().width / 120);
    let x = ((click_x - screen_x) / scale - 60) / 10, y = -((click_y - screen_y) / scale - 60) / 10;
    x = x.toFixed(2);
    y = y.toFixed(2)
    console.log(click_x, click_y);
    console.log(screen_x, screen_y);
    console.log(x, y);
    document.getElementById("inpY").value = y;
    document.getElementById("hehe").value =  x;
    console.log(document.getElementById("inpX").value);
    document.getElementById("inpX").value =  x;
    console.log(document.getElementById("inpX").value);
    if (validate(true, x, y))
    {
       document.getElementById("submit").click();
    }
}