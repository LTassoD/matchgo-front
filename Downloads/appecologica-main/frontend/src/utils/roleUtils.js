export const isAdmin = (role) => role === 'ADMIN';

export const canSeeOrders = (role) => role === 'ADMIN' || role === 'CHOFER';
