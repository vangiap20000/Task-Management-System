document.querySelectorAll('.btn-confirm-js').forEach(button => {
    button.addEventListener('click', () => {
        const url = button.getAttribute('data-url');
        const modal = new bootstrap.Modal(document.getElementById('modal-confirm'));
        document.querySelector('#modal-confirm form').setAttribute('action', url);
        modal.show();
    });
});
