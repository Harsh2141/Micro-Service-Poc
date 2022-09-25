--admintest
INSERT INTO public.users(
	created_date, email, enabled, password, role, user_name)
	VALUES (now(), 'admin@gmail.com', true, '$2a$10$/ONvKbsqM6tRevU/1nmgJO3yDVHN4dWy/gJA64pXpSpA36GIOcq5G', 'ROLE_ADMIN', 'admin');

--usertest
INSERT INTO public.users(
	created_date, email, enabled, password, role, user_name)
	VALUES (now(), 'user@gmail.com', true, '$2a$10$kN5Vz85ZLfcrsifOJIXaIuPZDfVF0LnMjx9KduWDVvowDymmIOXC2', 'ROLE_USER', 'user');