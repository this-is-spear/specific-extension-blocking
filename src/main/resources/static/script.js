function check(n) {
  if (n.checked) {
    add(n.id)
  } else {
    clear(n.id)
  }
}

function submit() {
  add($('input[name=name]').val());
}

function delete_extension(n) {
  clear(n.id);
}

function add(name) {
  let data = extracted(name);
  request_message(data, "POST");
}

function extracted(name) {
  return {
    "name": name
  };
}

function clear(name) {
  let data = extracted(name);
  request_message(data, "DELETE");
}

function request_message(data, method) {
  $.ajax(
      {
        url: "/extensions",
        type: method,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: success_call,
        error: error_call()
      }
  )
}

function success_call() {
  location.reload();
}

function error_call() {
  return request => {
    alert("전송실패 : " + request.responseText);
  };
}
