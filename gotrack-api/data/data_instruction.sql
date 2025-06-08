SELECT t.*, a.artist_id, a.band_id
FROM track t
JOIN album a ON t.album_id = a.id;