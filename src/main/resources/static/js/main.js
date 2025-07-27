document.querySelectorAll('.btn-confirm-js').forEach(button => {
    button.addEventListener('click', () => {
        const url = button.getAttribute('data-url');
        const modal = new bootstrap.Modal(document.getElementById('modal-confirm'));
        document.querySelector('#modal-confirm form').setAttribute('action', url);
        modal.show();
    });
});

function previewImage(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        const preview = document.getElementById('preview');
        preview.src = e.target.result;
        preview.classList.remove('hidden');
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

flatpickr("#due_date", {
    enableTime: false,
    dateFormat: "Y-m-d",
});

$('#priority').select2({
  theme: 'tailwindcss-3',
});

$('#status').select2({
  theme: 'tailwindcss-3',
});

$('#category_id').select2({
  theme: 'tailwindcss-3',
});

$('#label_id').select2({
  theme: 'tailwindcss-3',
});