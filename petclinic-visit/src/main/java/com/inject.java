package com;

import org.springframework.stereotype.Component;
import java.util.Map;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanTextMap;
import org.springframework.cloud.sleuth.util.TextMapUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
class ZipkinHttpSpanInjector implements HttpSpanInjector {

    private static final String HEADER_DELIMITER = "-";

    @Override
    public void inject(Span span, SpanTextMap map) {
        Map carrier = TextMapUtil.asMap(map);
        setHeader(map, carrier, Span.TRACE_ID_NAME, span.traceIdString());
        setIdHeader(map, carrier, Span.SPAN_ID_NAME, span.getSpanId());
        setHeader(map, carrier, Span.SAMPLED_NAME, span.isExportable() ? Span.SPAN_SAMPLED : Span.SPAN_NOT_SAMPLED);
        setHeader(map, carrier, Span.SPAN_NAME_NAME, span.getName());
        setIdHeader(map, carrier, Span.PARENT_ID_NAME, getParentId(span));
        setHeader(map, carrier, Span.PROCESS_ID_NAME, span.getProcessId());
        for (Map.Entry entry : span.baggageItems()) {
            map.put(prefixedKey(entry.getKey()), entry.getValue());
        }
    }

    
}

