const CART_IDS_STORAGE_KEY = 'zszpg-cart-ids'

export const getSavedCartIds = (): number[] => {
	const savedValue = localStorage.getItem(CART_IDS_STORAGE_KEY)
	return JSON.parse(savedValue ?? '[]') as number[]
}

export const saveCartId = (cartId: number) => {
	const uniqueCartIds = new Set([...getSavedCartIds(), cartId])
	localStorage.setItem(CART_IDS_STORAGE_KEY, JSON.stringify([...uniqueCartIds]))
}

export const removeSavedCartId = (cartIdToRemove: number) => {
	const remainingCartIds = getSavedCartIds().filter((cartId) => cartId !== cartIdToRemove)
	localStorage.setItem(CART_IDS_STORAGE_KEY, JSON.stringify(remainingCartIds))
}
