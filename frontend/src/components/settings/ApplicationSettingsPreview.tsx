import type { CSSProperties } from 'react'
import type { ApplicationSettings } from '../../types'

interface ApplicationSettingsPreviewProps {
	settings: ApplicationSettings
}

const ApplicationSettingsPreview = (props: ApplicationSettingsPreviewProps) => {
	const previewStyle: CSSProperties = {
		backgroundImage: props.settings.backgroundImageUrl
			? `linear-gradient(rgb(0 0 0 / 45%), rgb(0 0 0 / 45%)), url("${props.settings.backgroundImageUrl}")`
			: 'linear-gradient(135deg, #273248, #536976)',
		fontFamily: props.settings.fontFamily,
	}

	return (
		<section className="settings-preview" style={previewStyle}>
			<h2 style={{ color: props.settings.nameColor }}>{props.settings.restaurantName}</h2>
			<p style={{ color: props.settings.descriptionColor }}>
				{props.settings.restaurantDescription}
			</p>
		</section>
	)
}

export default ApplicationSettingsPreview
