document.addEventListener('DOMContentLoaded', async () => {
    const response = await fetch('/feeding-locations');
    const locations = await response.json();
    const tableBody = document.getElementById('locations-table-body');

    locations.forEach(loc => {
        const row = `
            <tr>
                <td>${loc.locationId}</td>
                <td>${loc.address || 'N/A'}</td>
                <td>${loc.description}</td>
                <td>${loc.lastFed || 'Not fed yet'}</td>
                <td>${loc.nextFeedingSchedule || 'Not scheduled'}</td>
                <td>
                    <a href="edit-feeding-point.html?id=${loc.locationId}">Edit</a> |
                    <button onclick="deleteLocation(${loc.locationId})">Delete</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
});

async function deleteLocation(id) {
    if (confirm('Are you sure you want to delete this location?')) {
        await fetch(/feeding-locations/${id}, { method: 'DELETE' });
        location.reload();
    }
}