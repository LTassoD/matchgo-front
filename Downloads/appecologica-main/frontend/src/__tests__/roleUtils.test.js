import { describe, expect, it } from 'vitest';
import { isAdmin, canSeeOrders } from '../utils/roleUtils.js';

describe('role utils', () => {
  it('valida roles de administrador', () => {
    expect(isAdmin('ADMIN')).toBe(true);
    expect(isAdmin('USER')).toBe(false);
  });

  it('permite ver órdenes a admin y chofer', () => {
    expect(canSeeOrders('ADMIN')).toBe(true);
    expect(canSeeOrders('CHOFER')).toBe(true);
    expect(canSeeOrders('USER')).toBe(false);
  });
});
