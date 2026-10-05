import type { CSSProperties } from 'react'
import type { ApplicationSettings } from '../../types'

interface OrdererHeroSectionProps {
	settings: ApplicationSettings | null
	onShowMenu: () => void
}

const OrdererHeroSection = (props: OrdererHeroSectionProps) => {
	const sectionStyle: CSSProperties = {
		backgroundImage: props.settings?.backgroundImageUrl
			? `linear-gradient(rgb(0 0 0 / 48%), rgb(0 0 0 / 48%)), url("${props.settings.backgroundImageUrl}")`
			: 'linear-gradient(135deg, #18212f, #52616b)',
		fontFamily: props.settings?.fontFamily ?? 'Arial, sans-serif',
	}

	return (
		<section className="orderer-section orderer-hero" style={sectionStyle}>
			<div className="orderer-hero-content">
				<p className="orderer-eyebrow">Witamy</p>
				<h1 style={{ color: props.settings?.nameColor ?? '#ffffff' }}>
					{props.settings?.restaurantName ?? 'Nasza restauracja'}
				</h1>
				<p style={{ color: props.settings?.descriptionColor ?? '#f1f1f1' }}>
					{props.settings?.restaurantDescription ??
						'Poznaj nasze menu i wybierz coś dla siebie.'}
				</p>
				<button type="button" onClick={props.onShowMenu}>
					Zobacz menu
				</button>
			</div>
		</section>
	)
}

export default OrdererHeroSection
