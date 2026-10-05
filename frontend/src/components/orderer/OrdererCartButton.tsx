interface OrdererCartButtonProps {
	itemCount: number
	onClick: () => void
}

const OrdererCartButton = (props: OrdererCartButtonProps) => (
	<button
		type="button"
		className="orderer-cart-button"
		onClick={props.onClick}
		aria-label={`Otwórz koszyk. Liczba produktów: ${props.itemCount}`}
	>
		<svg viewBox="0 0 24 24" aria-hidden="true">
			<path d="M3 4h2l2.2 10.1a2 2 0 0 0 2 1.6h7.7a2 2 0 0 0 1.9-1.4L21 7H6" />
			<circle cx="10" cy="20" r="1.25" />
			<circle cx="18" cy="20" r="1.25" />
		</svg>
		{props.itemCount > 0 && <span>{props.itemCount}</span>}
	</button>
)

export default OrdererCartButton
